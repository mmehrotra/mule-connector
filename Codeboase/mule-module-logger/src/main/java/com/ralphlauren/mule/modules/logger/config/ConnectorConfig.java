package com.ralphlauren.mule.modules.logger.config;

import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Summary;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Required;
import org.mule.api.annotations.param.Default;

/**
 * 
 * ConnectorConfig provide an application 
 * name to log as part of standard logging
 *
 */
@Configuration(friendlyName = "Logger-Configuration")
public class ConnectorConfig {

    @Configurable
    @Required
    @FriendlyName("Application Name or Id")
    @Summary("Provide an appication name to log as part of standard logging.")
    private String applicationName;

    @Configurable
    @Required
    @FriendlyName("Logger Category")
    @Summary("Provide a logger category against which logging would be done.")
    private String loggerCategory;

    @Configurable
    @Default("true")
    @FriendlyName("Log Message Id?")
    @Summary("If enabled, #[message.id] is logged in log messages.")
    private boolean logMessageId;

    @Configurable
    @Default("true")
    @FriendlyName("Log Correlation Id?")
    @Summary("If enabled, #[message.correlationId] is logged in log messages.")
    private boolean logCorrelationId;

    @Configurable
    @Default("true")
    @FriendlyName("Skip Correlation Id if its not available?")
    @Summary("If enabled, #[message.correlationId] is logged only when its available in message. "
                + "This property has no effect if 'Enable Correlation Id logging?' is disabled.")
    private boolean skipMissingCorrelationId;

    /**
     * @return the applicationName
     */
    public final String getApplicationName() {
        return applicationName;
    }

    /**
     * @param applicationName
     *            the applicationName to set
     */
    public final void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * @return the loggerCategory
     */
    public final String getLoggerCategory() {
        return loggerCategory;
    }

    /**
     * @param loggerCategory
     *            the loggerCategory to set
     */
    public final void setLoggerCategory(String loggerCategory) {
        this.loggerCategory = loggerCategory;
    }

    /**
     * @return the logMessageId
     */
    public final boolean isLogMessageId() {
        return logMessageId;
    }

    /**
     * @param logMessageId
     *            the logMessageId to set
     */
    public final void setLogMessageId(boolean logMessageId) {
        this.logMessageId = logMessageId;
    }

    /**
     * @return the logCorrelationId
     */
    public final boolean isLogCorrelationId() {
        return logCorrelationId;
    }

    /**
     * @param logCorrelationId
     *            the logCorrelationId to set
     */
    public final void setLogCorrelationId(boolean logCorrelationId) {
        this.logCorrelationId = logCorrelationId;
    }

    /**
     * @return the skipMissingCorrelationId
     */
    public final boolean isSkipMissingCorrelationId() {
        return skipMissingCorrelationId;
    }

    /**
     * @param skipMissingCorrelationId
     *            the skipMissingCorrelationId to set
     */
    public final void setSkipMissingCorrelationId(boolean skipMissingCorrelationId) {
        this.skipMissingCorrelationId = skipMissingCorrelationId;
    }

}
