
package com.ralphlauren.mule.modules.countdownlatch.adapters;

import com.ralphlauren.mule.modules.countdownlatch.MuleModuleCountdownLatchConnector;
import javax.annotation.Generated;
import org.mule.api.MetadataAware;


/**
 * A <code>MuleModuleCountdownLatchConnectorMetadataAdapter</code> is a wrapper around {@link MuleModuleCountdownLatchConnector } that adds support for querying metadata about the extension.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2017-01-30T02:54:21+05:30", comments = "Build UNNAMED.2613.77421cc")
public class MuleModuleCountdownLatchConnectorMetadataAdapter
    extends MuleModuleCountdownLatchConnectorCapabilitiesAdapter
    implements MetadataAware
{

    private final static String MODULE_NAME = "CountDown Latch";
    private final static String MODULE_VERSION = "2.1.1-RELEASE";
    private final static String DEVKIT_VERSION = "3.7.1";
    private final static String DEVKIT_BUILD = "UNNAMED.2613.77421cc";
    private final static String MIN_MULE_VERSION = "3.6";

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    public String getMinMuleVersion() {
        return MIN_MULE_VERSION;
    }

}
