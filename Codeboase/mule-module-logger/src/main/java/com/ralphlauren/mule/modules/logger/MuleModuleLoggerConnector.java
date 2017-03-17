package com.ralphlauren.mule.modules.logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.expression.ExpressionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ralphlauren.mule.modules.logger.config.ConnectorConfig;

/**
 * Standard ESB logging component to construct log messages in standard format.
 * Following two message formats are supported.
 * <p>
 * <code>
 * [Application=[<app name>]] [MessageID=[muleMessage.getUniqueId()]] 
 * [CorrelationID=[muleMessage.getCorrelationId()]] 
 * [ProcessType=[<Mule flow name or provided process type>]] 
 * [Message=[<actual log message>]] 
 * [Payload=[muleMessage.getPayload()]]
 * </code>
 * </p>
 * 
 * and 
 * 
 * <p>
 * <code>
 * [Application=[<app name>]] [MessageID=[muleMessage.getUniqueId()]] 
 * [CorrelationID=[muleMessage.getCorrelationId()]] 
 * [ProcessType=[<Mule flow name or provided process type>]] 
 * [ErrorCode=[]] [Message=[<actual log message>]] 
 * [Payload=[muleMessage.getPayload()]]
 * <full actual exception trace....>
 * </code>
 * </p>
 * 
 * For details of the format and what each part in the format 
 * means please see 
 * {@link #logMessage(LogLevelEnum, String, String, boolean, MuleMessage, MuleEvent)}
 * and {@link #logMessage(LogLevelEnum, String, String, boolean, MuleMessage, MuleEvent)}
 * methods.
 * 
 * @author vwali
 *
 */
@Connector(name = "logger2", friendlyName = "Logger2", minMuleVersion = "3.6", schemaVersion = "1.0", 
           description = "Enhanced Mule Logger Module with standardized log message formats.")
public class MuleModuleLoggerConnector {

    @Config
    private ConnectorConfig config;

    @Inject
    private MuleContext muleContext;
    
    @Inject
    private ExpressionManager expressionManager = null;
    
    private static Logger logger = null;

    /**
     * Initialization method to initialize the logger as per the category
     * provided. {@link LoggerFactory} internally tracks the logger category
     * association so repeated calls to {@link LoggerFactory#getLogger(String)}
     * should not have an overhead. 
     * 
     * <br />
     * 
     * This method is synchronized to ensure we only create one instance of the
     * logger per category per app.
     */
    @PostConstruct
    public final synchronized void init() {
        if (null == logger) {
            logger = LoggerFactory.getLogger(config.getLoggerCategory());
        }
    }
    
    /**
     * Processor to log a message. This processor logs the message in the following format:
     * <p>
     * <code>
     * [Application=[<app name>]] [MessageID=[muleMessage.getUniqueId()]] 
     * [CorrelationID=[muleMessage.getCorrelationId()]] 
     * [ProcessType=[<Mule flow name or provided process type>]] 
     * [Message=[<actual log message>]] 
     * [Payload=[muleMessage.getPayload()]]
     * </code>
     * </p>
     * Where:
     * <ul>
     * <li><b>Application</b> is the name of the application.</li>
     * <li><b>MessageID</b> is the value we get from {@link MuleMessage#getUniqueId()} 
     * which uniquely identifies a message in cluster.</li>
     * <li><b>CorrelationID</b> is the value we get from {@link MuleMessage#getCorrelationId()} 
     * which can be used to correlate messages across cluster.</li>
     * <li><b>ProcessType</b> is the name of the flow / sub-flow / process 
     * which logged the message. </li>
     * <li><b>Message</b> is the actual log message to print. Log message can 
     * also include MEL expressions.</li>
     * <li><b>Payload</b> is the value in {@link MuleMessage#getPayload()}</li>
     * </ul>
     * @param logLevel a type of {@link LogLevelEnum} is expected.
     *                    Strings are also accepted as long as they are either
     *                     {@link LogLevelEnum#DEBUG}, {@link LogLevelEnum#INFO}
     *                     , {@link LogLevelEnum#WARN}, {@link LogLevelEnum#TRACE}. 
     *                    Default value is {@link LogLevelEnum#INFO}
     * @param logMessage the message to log. This can include 
     *                     MEL expressions as well.
     * @param processType name of the flow or process which 
     *                         originated the message. Incase the originator of 
     *                         the log message is a sub-flow, its parent flow 
     *                         is recorded as the process type.
     * @param logPayload boolean choice to log the payload or not.
     * @param muleMessage the originating {@link MuleMessage}
     * @param muleEvent the originating {@link MuleEvent}
     */
    @Processor
    public final void logMessage(@Default("INFO") LogLevelEnum logLevel
                                , String logMessage, @Optional String processType
                                , @Default("false") boolean logPayload
                                , MuleMessage muleMessage
                                , MuleEvent muleEvent) {
        if (!StringUtils.isBlank(logMessage)) {
            this.processLogMessageRequest(logLevel, logMessage, processType
                                                , logPayload, muleMessage, muleEvent);
        }
    }
    
    /**
     * Processor to log a message in ERROR log level. 
     * This processor logs the message in the following format:
     * <p>
     * <code>
     * [Application=[<app name>]] [MessageID=[muleMessage.getUniqueId()]] 
     * [CorrelationID=[muleMessage.getCorrelationId()]] 
     * [ProcessType=[<Mule flow name or provided process type>]] 
     * [ErrorCode=[]] [Message=[<actual log message>]] 
     * [Payload=[muleMessage.getPayload()]]
     * <full actual exception trace....>
     * </code>
     * </p>
     * Where:
     * <ul>
     * <li><b>Application</b> is the name of the application.</li>
     * <li><b>MessageID</b> is the value we get from {@link MuleMessage#getUniqueId()} 
     * which uniquely identifies a message in cluster.</li>
     * <li><b>CorrelationID</b> is the value we get from {@link MuleMessage#getCorrelationId()} 
     * which can be used to correlate messages across cluster.</li>
     * <li><b>ProcessType</b> is the name of the flow / sub-flow / process 
     * which logged the message. </li>
     * <li><b>ErrorCode</b> is the system error code created by ESB. 
     * Error codes range from DSB1001 to DSBxxxx</li>
     * <li><b>Message</b> is the actual log message to print. Log message can 
     * also include MEL expressions.</li>
     * <li><b>Payload</b> is the value in {@link MuleMessage#getPayload()}.
     * This is logged if {@code logPayload} parameter is true.</li>
     * <li>Actual exception trace is added after the log message if the 
     * {@code logException} parameter is true and message payload 
     * includes an exception object.</li>
     * </ul>
     * 
     * @param logMessage is the message to log. This can include 
     *             MEL expressions as well.
     * @param errorCode is the system error code created by ESB. 
     *                         Error codes range from DSB1001 to DSBxxxx
     * @param processType name of the flow or process which 
     *                         originated the message. Incase the originator of 
     *                         the log message is a sub-flow, its parent flow 
     *                         is recorded as the process type.
     * @param logException boolean choice to log the exception or not.
     *                         Default value is true.
     * @param logPayload boolean choice to log the payload or not.
     * @param muleMessage the originating {@link MuleMessage}
     * @param muleEvent the originating {@link MuleEvent}
     */
    @Processor
    public final void logError(String logMessage, String errorCode, @Optional String processType
                            , @Default("true") boolean logException
                            , @Default("false") boolean logPayload
                            , MuleMessage muleMessage, MuleEvent muleEvent) {
        if (!StringUtils.isBlank(logMessage)) {
            this.processLogMessageRequest(logMessage, errorCode, processType, logException
                                                , logPayload, muleMessage, muleEvent);
        }
    }

    private void processLogMessageRequest(LogLevelEnum logLevel, String logMessage, String processType
                                                , boolean logPayload, MuleMessage muleMessage
                                                , MuleEvent muleEvent) {
        String parsedMessage = this.constructLogMessage(logMessage, null, processType, logPayload
                                                            , muleMessage, muleEvent);
        switch (logLevel) {
            case INFO:
                if (logger.isInfoEnabled()) {
                    logger.info(parsedMessage);
                }
                break;
            case WARN:
                if (logger.isWarnEnabled()) {
                    logger.warn(parsedMessage);
                }
                break;
            case TRACE:
                if (logger.isTraceEnabled()) {
                    logger.trace(parsedMessage);
                }
                break;
            case DEBUG:
            default://also logs in debug
                if (logger.isDebugEnabled()) {
                    logger.debug(parsedMessage);
                }
                break;
        }
    }
    
    /**
     * Constructs the exception based log message. If {@code logException} 
     * is true then ascertains if the {@link MuleMessage#getExceptionPayload()}
     * is available or not. If found logs the exception else just logs the 
     * base log message.
     * 
     * @param logMessage is the message to log. This can include 
     *                         MEL expressions as well.
     * @param errorCode is the system error code created by ESB. 
     *                         Error codes range from DSB1001 to DSBxxxx
     * @param processType name of the flow or process which 
     *                         originated the message. Incase the originator of 
     *                         the log message is a sub-flow, its parent flow 
     *                         is recorded as the process type.
     * @param logException boolean choice to log the exception or not.
     *                         Default value is true.
     * @param logPayload boolean choice to log the payload or not.
     * @param muleMessage the originating {@link MuleMessage}
     * @param muleEvent the originating {@link MuleEvent}
     */
    private void processLogMessageRequest(String logMessage, String errorCode, String processType
                                                , boolean logException, boolean logPayload
                                                , MuleMessage muleMessage, MuleEvent muleEvent) {
        if (logger.isErrorEnabled()) {
            String parsedMessage = this.constructLogMessage(logMessage, errorCode, processType
                                                                , logPayload, muleMessage, muleEvent);
            if (logException) {
                ExceptionPayload exceptionPayload = muleMessage.getExceptionPayload();
                if ((null != exceptionPayload) && (null != exceptionPayload.getException())) {
                    logger.error(parsedMessage, exceptionPayload.getException());
                } else {
                    logger.error(parsedMessage);
                }
            } else {
                logger.error(parsedMessage);
            }
        }
    }
    
    /**
     * Constructs the base log message.
     * 
     * @param logMessage is the message to log. This can include 
     *             MEL expressions as well.
     * @param errorCode is the system error code created by ESB. 
     *                         Error codes range from DSB1001 to DSBxxxx
     * @param processType name of the flow or process which 
     *                         originated the message. Incase the originator of 
     *                         the log message is a sub-flow, its parent flow 
     *                         is recorded as the process type.
     * @param logPayload boolean choice to log the payload or not.
     * @param muleMessage the originating {@link MuleMessage}
     * @param muleEvent the originating {@link MuleEvent}
     * 
     * @return the evaluated and parsed base log message in standard format.
     */
    private String constructLogMessage(String logMessage, String errorCode, String processType
                                            , boolean logPayload, MuleMessage muleMessage
                                            , MuleEvent muleEvent) {
        StringBuilder builder = new StringBuilder();
        builder.append("[Application=[").append(config.getApplicationName()).append("]] ");
        
        if (config.isLogMessageId()) {
            builder.append("[MessageID=[").append(muleMessage.getUniqueId()).append("]] ");
        }
        
        if (config.isLogCorrelationId()) {
            String correlationId = muleMessage.getCorrelationId();
            if (!StringUtils.isBlank(correlationId)) {
                builder.append("[CorrelationID=[").append(correlationId).append("]] ");
            } else if (!config.isSkipMissingCorrelationId()) {
                builder.append("[CorrelationID=[]] ");
            }
        }
        
        if (!StringUtils.isBlank(processType)) {
            builder.append("[ProcessType=[").append(processType).append("]] ");
        } else {
            builder.append("[ProcessType=[").append(muleEvent.getFlowConstruct().getName()).append("]] ");
        }
        
        if (!StringUtils.isBlank(errorCode)) {
            builder.append("[ErrorCode=[").append(errorCode).append("]] ");
        }
        
        String parsedMessage = this.expressionManager.parse(logMessage, muleEvent);
        builder.append("[Message=[").append(parsedMessage).append("]] ");
        
        if (logPayload) { 
            builder.append("[Payload=[").append(muleMessage.getPayload()).append("]] ");
        }
        return builder.toString();
    }
    
    /**
     * @return the muleContext
     */
    public final MuleContext getMuleContext() {
        return muleContext;
    }

    /**
     * @param muleContext the muleContext to set
     */
    public void setMuleContext(MuleContext muleContext) { //NOSONAR
        this.muleContext = muleContext;
    }

    /**
     * @return the expressionManager
     */
    public final ExpressionManager getExpressionManager() {
        return expressionManager;
    }

    /**
     * @param expressionManager the expressionManager to set
     */
    public final void setExpressionManager(ExpressionManager expressionManager) {
        this.expressionManager = expressionManager;
    }

    /**
     * @return the config
     */
    public final ConnectorConfig getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public final void setConfig(ConnectorConfig config) {
        this.config = config;
    }
}
