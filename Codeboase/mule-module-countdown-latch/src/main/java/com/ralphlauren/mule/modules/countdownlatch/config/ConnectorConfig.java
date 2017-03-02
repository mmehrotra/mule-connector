package com.ralphlauren.mule.modules.countdownlatch.config;

import java.util.concurrent.TimeUnit;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Required;
import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Summary;
import org.mule.api.annotations.param.Default;

/**
 * 
 * Connector Configuration provide an application name for 
 * which count down latch would be created
 *
 */
@Configuration(friendlyName = "CountDownLatch-Configuration")
public class ConnectorConfig {

    @Configurable
    @Required
    @FriendlyName("Application Name or Id")
    @Summary("Provide an appication name for which count down latch would be created.")
    private String applicationName;

    @Configurable
    @Required
    @Default("30")
    @Summary("Maximum time for which latch will wait for the count downs to reach zero? Default is 15 minutes.")
    private long maxLatchAwaitTime;

    @Configurable
    @Required
    @Default("MINUTES")
    @Summary("Default time unit to wait for the latch to count down to zero? Default is 15 minutes.")
    private TimeUnit timeUnit;

    @Configurable
    @Required
    @Default("true")
    @FriendlyName("Log Latch Count Down Number")
    @Summary("If enabled, logs the count down number of the latch.")
    private boolean logLatchCountDowns;

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

    /**
     * @return the timeUnit
     */
    public final TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /**
     * @param timeUnit
     *            the timeUnit to set
     */
    public final void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    /**
     * @return the logLatchCountDowns
     */
    public final boolean isLogLatchCountDowns() {
        return logLatchCountDowns;
    }

    /**
     * @param logLatchCountDowns
     *            the logLatchCountDowns to set
     */
    public final void setLogLatchCountDowns(boolean logLatchCountDowns) {
        this.logLatchCountDowns = logLatchCountDowns;
    }

    /**
     * @return the maxLatchAwaitTime
     */
    public final long getMaxLatchAwaitTime() {
        return maxLatchAwaitTime;
    }

    /**
     * @param maxLatchAwaitTime
     *            the maxLatchAwaitTime to set
     */
    public final void setMaxLatchAwaitTime(long maxLatchAwaitTime) {
        this.maxLatchAwaitTime = maxLatchAwaitTime;
    }
}
