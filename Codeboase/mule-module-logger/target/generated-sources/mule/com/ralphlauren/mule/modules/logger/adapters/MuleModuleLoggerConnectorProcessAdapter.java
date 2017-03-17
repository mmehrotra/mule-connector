
package com.ralphlauren.mule.modules.logger.adapters;

import com.ralphlauren.mule.modules.logger.MuleModuleLoggerConnector;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>MuleModuleLoggerConnectorProcessAdapter</code> is a wrapper around {@link MuleModuleLoggerConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2017-01-30T02:53:59+05:30", comments = "Build UNNAMED.2613.77421cc")
public class MuleModuleLoggerConnectorProcessAdapter
    extends MuleModuleLoggerConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<MuleModuleLoggerConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, MuleModuleLoggerConnectorCapabilitiesAdapter> getProcessTemplate() {
        final MuleModuleLoggerConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,MuleModuleLoggerConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, MuleModuleLoggerConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, MuleModuleLoggerConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
