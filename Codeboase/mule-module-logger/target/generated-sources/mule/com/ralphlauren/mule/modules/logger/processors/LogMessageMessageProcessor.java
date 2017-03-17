
package com.ralphlauren.mule.modules.logger.processors;

import com.ralphlauren.mule.modules.logger.LogLevelEnum;
import com.ralphlauren.mule.modules.logger.MuleModuleLoggerConnector;
import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.config.ConfigurationException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.common.DefaultResult;
import org.mule.common.FailureType;
import org.mule.common.Result;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultPojoMetaDataModel;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.OperationMetaDataEnabled;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.DataTypeFactory;
import org.mule.devkit.processor.DevkitBasedMessageProcessor;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * LogMessageMessageProcessor invokes the {@link com.ralphlauren.mule.modules.logger.MuleModuleLoggerConnector#logMessage(com.ralphlauren.mule.modules.logger.LogLevelEnum, java.lang.String, java.lang.String, boolean, org.mule.api.MuleMessage, org.mule.api.MuleEvent)} method in {@link MuleModuleLoggerConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2017-01-30T02:53:59+05:30", comments = "Build UNNAMED.2613.77421cc")
public class LogMessageMessageProcessor
    extends DevkitBasedMessageProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object logLevel;
    protected LogLevelEnum _logLevelType;
    protected Object logMessage;
    protected String _logMessageType;
    protected Object processType;
    protected String _processTypeType;
    protected Object logPayload;
    protected boolean _logPayloadType;
    protected Object muleMessage;
    protected MuleMessage _muleMessageType;
    protected Object muleEvent;
    protected MuleEvent _muleEventType;

    public LogMessageMessageProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    @Override
    public void start()
        throws MuleException
    {
        super.start();
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Sets processType
     * 
     * @param value Value to set
     */
    public void setProcessType(Object value) {
        this.processType = value;
    }

    /**
     * Sets logLevel
     * 
     * @param value Value to set
     */
    public void setLogLevel(Object value) {
        this.logLevel = value;
    }

    /**
     * Sets logPayload
     * 
     * @param value Value to set
     */
    public void setLogPayload(Object value) {
        this.logPayload = value;
    }

    /**
     * Sets muleEvent
     * 
     * @param value Value to set
     */
    public void setMuleEvent(Object value) {
        this.muleEvent = value;
    }

    /**
     * Sets muleMessage
     * 
     * @param value Value to set
     */
    public void setMuleMessage(Object value) {
        this.muleMessage = value;
    }

    /**
     * Sets logMessage
     * 
     * @param value Value to set
     */
    public void setLogMessage(Object value) {
        this.logMessage = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public MuleEvent doProcess(final MuleEvent event)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(null, false, event);
            final LogLevelEnum _transformedLogLevel = ((LogLevelEnum) evaluateAndTransform(getMuleContext(), event, LogMessageMessageProcessor.class.getDeclaredField("_logLevelType").getGenericType(), null, logLevel));
            final String _transformedLogMessage = ((String) evaluateAndTransform(getMuleContext(), event, LogMessageMessageProcessor.class.getDeclaredField("_logMessageType").getGenericType(), null, logMessage));
            final String _transformedProcessType = ((String) evaluateAndTransform(getMuleContext(), event, LogMessageMessageProcessor.class.getDeclaredField("_processTypeType").getGenericType(), null, processType));
            final Boolean _transformedLogPayload = ((Boolean) evaluateAndTransform(getMuleContext(), event, LogMessageMessageProcessor.class.getDeclaredField("_logPayloadType").getGenericType(), null, logPayload));
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    ((MuleModuleLoggerConnector) object).logMessage(_transformedLogLevel, _transformedLogMessage, _transformedProcessType, _transformedLogPayload, event.getMessage(), event);
                    return null;
                }

            }
            , this, event);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Result<MetaData> getInputMetaData() {
        return new DefaultResult<MetaData>(null, (Result.Status.SUCCESS));
    }

    @Override
    public Result<MetaData> getOutputMetaData(MetaData inputMetadata) {
        return new DefaultResult<MetaData>(new DefaultMetaData(getPojoOrSimpleModel(void.class)));
    }

    private MetaDataModel getPojoOrSimpleModel(Class clazz) {
        DataType dataType = DataTypeFactory.getInstance().getDataType(clazz);
        if (DataType.POJO.equals(dataType)) {
            return new DefaultPojoMetaDataModel(clazz);
        } else {
            return new DefaultSimpleMetaDataModel(dataType);
        }
    }

    public Result<MetaData> getGenericMetaData(MetaDataKey metaDataKey) {
        ConnectorMetaDataEnabled connector;
        try {
            connector = ((ConnectorMetaDataEnabled) findOrCreate(null, false, null));
            try {
                Result<MetaData> metadata = connector.getMetaData(metaDataKey);
                if ((Result.Status.FAILURE).equals(metadata.getStatus())) {
                    return metadata;
                }
                if (metadata.get() == null) {
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at MuleModuleLoggerConnector at logMessage retrieving was successful but result is null");
                }
                return metadata;
            } catch (Exception e) {
                return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
            }
        } catch (ClassCastException cast) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error getting metadata, there was no connection manager available. Maybe you're trying to use metadata from an Oauth connector");
        } catch (ConfigurationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (RegistrationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (IllegalAccessException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (InstantiationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (Exception e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        }
    }

}
