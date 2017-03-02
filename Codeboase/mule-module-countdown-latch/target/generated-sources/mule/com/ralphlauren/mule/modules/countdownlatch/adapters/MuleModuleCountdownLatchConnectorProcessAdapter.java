
package com.ralphlauren.mule.modules.countdownlatch.adapters;

import com.ralphlauren.mule.modules.countdownlatch.MuleModuleCountdownLatchConnector;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>MuleModuleCountdownLatchConnectorProcessAdapter</code> is a wrapper around {@link MuleModuleCountdownLatchConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2017-01-30T02:54:21+05:30", comments = "Build UNNAMED.2613.77421cc")
public class MuleModuleCountdownLatchConnectorProcessAdapter
    extends MuleModuleCountdownLatchConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<MuleModuleCountdownLatchConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, MuleModuleCountdownLatchConnectorCapabilitiesAdapter> getProcessTemplate() {
        final MuleModuleCountdownLatchConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,MuleModuleCountdownLatchConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, MuleModuleCountdownLatchConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, MuleModuleCountdownLatchConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
