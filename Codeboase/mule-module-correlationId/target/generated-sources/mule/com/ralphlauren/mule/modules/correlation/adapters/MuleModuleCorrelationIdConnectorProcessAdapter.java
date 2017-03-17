
package com.ralphlauren.mule.modules.correlation.adapters;

import com.ralphlauren.mule.modules.correlation.MuleModuleCorrelationIdConnector;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>MuleModuleCorrelationIdConnectorProcessAdapter</code> is a wrapper around {@link MuleModuleCorrelationIdConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2017-01-30T02:54:10+05:30", comments = "Build UNNAMED.2613.77421cc")
public class MuleModuleCorrelationIdConnectorProcessAdapter
    extends MuleModuleCorrelationIdConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<MuleModuleCorrelationIdConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, MuleModuleCorrelationIdConnectorCapabilitiesAdapter> getProcessTemplate() {
        final MuleModuleCorrelationIdConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,MuleModuleCorrelationIdConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, MuleModuleCorrelationIdConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, MuleModuleCorrelationIdConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
