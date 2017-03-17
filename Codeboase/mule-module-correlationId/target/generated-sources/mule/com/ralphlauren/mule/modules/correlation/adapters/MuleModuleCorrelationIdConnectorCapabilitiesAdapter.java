
package com.ralphlauren.mule.modules.correlation.adapters;

import com.ralphlauren.mule.modules.correlation.MuleModuleCorrelationIdConnector;
import javax.annotation.Generated;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;


/**
 * A <code>MuleModuleCorrelationIdConnectorCapabilitiesAdapter</code> is a wrapper around {@link MuleModuleCorrelationIdConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2017-01-30T02:54:10+05:30", comments = "Build UNNAMED.2613.77421cc")
public class MuleModuleCorrelationIdConnectorCapabilitiesAdapter
    extends MuleModuleCorrelationIdConnector
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
