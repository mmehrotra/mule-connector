
package com.ralphlauren.mule.modules.logger.adapters;

import com.ralphlauren.mule.modules.logger.MuleModuleLoggerConnector;
import javax.annotation.Generated;
import org.mule.api.MetadataAware;


/**
 * A <code>MuleModuleLoggerConnectorMetadataAdapter</code> is a wrapper around {@link MuleModuleLoggerConnector } that adds support for querying metadata about the extension.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2017-01-30T02:53:59+05:30", comments = "Build UNNAMED.2613.77421cc")
public class MuleModuleLoggerConnectorMetadataAdapter
    extends MuleModuleLoggerConnectorCapabilitiesAdapter
    implements MetadataAware
{

    private final static String MODULE_NAME = "Logger2";
    private final static String MODULE_VERSION = "1.3-RELEASE";
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
