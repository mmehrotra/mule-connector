
package com.ralphlauren.mule.modules.countdownlatch.adapters;

import com.ralphlauren.mule.modules.countdownlatch.MuleModuleCountdownLatchConnector;
import javax.annotation.Generated;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;


/**
 * A <code>MuleModuleCountdownLatchConnectorCapabilitiesAdapter</code> is a wrapper around {@link MuleModuleCountdownLatchConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2017-01-30T02:54:21+05:30", comments = "Build UNNAMED.2613.77421cc")
public class MuleModuleCountdownLatchConnectorCapabilitiesAdapter
    extends MuleModuleCountdownLatchConnector
    implements Capabilities
{


    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(ModuleCapability capability) {
        if (capability == ModuleCapability.LIFECYCLE_CAPABLE) {
            return true;
        }
        return false;
    }

}
