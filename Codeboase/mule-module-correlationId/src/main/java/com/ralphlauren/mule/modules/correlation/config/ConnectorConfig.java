package com.ralphlauren.mule.modules.correlation.config;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Required;
import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Summary;

/**
 * 
 * This class is responsible to Provide an application name 
 * for which correlation id would be generated
 *
 */
@Configuration(friendlyName = "CorrelationId-Configuration")
public class ConnectorConfig {

    @Configurable
    @Required
    @FriendlyName("Application Name or Id")
    @Summary("Provide an appication name for which correlation id would be generated.")
    private String applicationName;

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
}
