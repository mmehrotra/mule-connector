
package com.ralphlauren.mule.modules.countdownlatch.adapters;

import com.ralphlauren.mule.modules.countdownlatch.MuleModuleCountdownLatchConnector;
import javax.annotation.Generated;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.common.MuleVersion;
import org.mule.config.MuleManifest;
import org.mule.config.i18n.CoreMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A <code>MuleModuleCountdownLatchConnectorLifecycleInjectionAdapter</code> is a wrapper around {@link MuleModuleCountdownLatchConnector } that adds lifecycle methods to the pojo. This adapter also allows the injection of several Mule facilities when a MuleContext is set.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2017-01-30T02:54:21+05:30", comments = "Build UNNAMED.2613.77421cc")
public class MuleModuleCountdownLatchConnectorLifecycleInjectionAdapter
    extends MuleModuleCountdownLatchConnectorMetadataAdapter
    implements MuleContextAware, Disposable, Initialisable, Startable, Stoppable
{


    @Override
    public void start()
        throws MuleException
    {
    }

    @Override
    public void stop()
        throws MuleException
    {
    }

    @Override
    public void initialise()
        throws InitialisationException
    {
        Logger log = LoggerFactory.getLogger(MuleModuleCountdownLatchConnectorLifecycleInjectionAdapter.class);
        MuleVersion connectorVersion = new MuleVersion("3.6");
        MuleVersion muleVersion = new MuleVersion(MuleManifest.getProductVersion());
        if (!muleVersion.atLeastBase(connectorVersion)) {
            throw new InitialisationException(CoreMessages.minMuleVersionNotMet(this.getMinMuleVersion()), this);
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void setMuleContext(MuleContext context) {
        super.setMuleContext(context);
    }

}
