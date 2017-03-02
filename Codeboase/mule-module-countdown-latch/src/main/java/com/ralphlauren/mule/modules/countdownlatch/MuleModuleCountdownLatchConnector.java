package com.ralphlauren.mule.modules.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.registry.MuleRegistry;
import org.mule.api.registry.RegistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICountDownLatch;
import com.mulesoft.mule.cluster.hazelcast.HazelcastManager;
import com.ralphlauren.mule.modules.countdownlatch.config.ConnectorConfig;

/**
 * Mule module to manage cluster safe countdown latch mechanisms. The Module
 * uses {@link com.hazelcast.core.ICountDownLatch} when it detects that the app
 * is in a clustered environment.
 * <p>
 * In a standalone single JVM based setup,
 * {@link java.util.concurrent.CountDownLatch} is used instead.
 * </p>
 * <p>
 * All this is transparent to the Mule app and the user and is handled
 * automatically.
 * </p>
 * 
 * <p>
 * All MuleModuleCountdownLatchConnector processor methods throw
 * {@link CountDownLatchException} when anything goes wrong or some mandatory
 * values are missing or wrong. App flows should catch this exception type and
 * handle it accordingly. This is important because a normal usecase would want
 * to decrement the latch count in the main flows or in the exception strategy
 * blocks if an exception happened in the flow. But if the exception happened
 * because of this module then the latch may get decremented twice which is not
 * right.
 * </p>
 * 
 * @author vwali
 *
 */
@Connector(name = "countdown-latch", friendlyName = "CountDown Latch", minMuleVersion = "3.6", 
           schemaVersion = "1.0", description = "Mule Module CountdownLatch")
public class MuleModuleCountdownLatchConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(MuleModuleCountdownLatchConnector.class);

    @Config
    private ConnectorConfig config;

    @Inject
    private MuleContext muleContext;
    
    private static final String HAZELCAST_MANAGER_ID = "_muleHazelcastManager";
    
    /**
     * Creates a Countdown latch for use in the application. This processor uses
     * {@link org.mule.util.lock.LockFactory} to make sure only one latch is
     * Created for the provided {@code latchName}
     * 
     * @param latchName
     *            - the name of the Latch. Once created, the latch can be
     *            referenced by this name later in the app flows.
     * @param totalCounts
     *            - the total counts of the latch. This is the values from which
     *            the latch counts down.
     * @param muleMessage
     *            - the optional mule message
     * @throws CountDownLatchException
     *             throws this exception if:
     *             <p>
     *             <ul>
     *             <li>total counts is less than or equal to 1 OR</li>
     *             <li>{@code latchName} is empty or blank OR</li>
     *             <li>total counts could not be set in the clustered
     *             {@link ICountDownLatch} OR</li>
     *             <li>stand alone Single JVM based {@link CountDownLatch} could
     *             not be registered in the Mule registry.</li>
     *             <ul>
     *             </p>
     */
    @Processor
    public final void create(String latchName, int totalCounts, MuleMessage muleMessage) throws CountDownLatchException {
        if (totalCounts <= 0) {
            String msg = "Latch total counts give are -> " + totalCounts 
                                + " Total counts must be more than zero.";
            LOGGER.error(msg);
            throw new CountDownLatchException(msg);
        }
        if (StringUtils.isBlank(latchName)) {
            String msg = "Latch name cannot be empty.";
            LOGGER.error(msg);
            throw new CountDownLatchException(msg);
        }
        Lock lock = muleContext.getLockFactory().createLock(config.getApplicationName());
        lock.lock();
        String fullLatchName = config.getApplicationName() + "-" + latchName;
        try {
            Object hazelcastManager = this.muleContext.getRegistry().get(HAZELCAST_MANAGER_ID);
            if (null != hazelcastManager) {
                // we are in a clustered setup.
                LOGGER.info("_muleHazelcastManager found in Mule registry. "
                                    + "Creating cluster aware CountDownLatch.");
                this.createClusterAwareCountDownLatch(fullLatchName, totalCounts, (HazelcastManager) hazelcastManager);
            } else {
                //single JVM stuff just use a standard latch and put it in registry
                LOGGER.info("_muleHazelcastManager was NOT FOUND in Mule registry. "
                                    + "Creating single JVM bound CountDownLatch.");
                this.createSingleJVMBoundCountDownLatch(fullLatchName, totalCounts);
            }
        } finally {
            lock.unlock();
        }
    }
    
    
    /**
     * Reset a Countdown latch for use in the application. This processor uses
     * {@link org.mule.util.lock.LockFactory} to make sure only one latch is
     * reset for the provided {@code latchName}
     * 
     * @param latchName
     *            - the name of the Latch. Once created, the latch can be
     *            referenced by this name later in the app flows.
     * @param muleMessage
     *            - the optional mule message
     * @throws CountDownLatchException
     *             throws this exception if:
     *             <p>
     *             <ul>
     *             <li>{@code latchName} is empty or blank OR</li>
     *             <li>total counts could not be set in the clustered
     *             {@link ICountDownLatch} OR</li>
     *             <li>stand alone Single JVM based {@link CountDownLatch} could
     *             not be registered in the Mule registry.</li>
     *             <ul>
     *             </p>
     */
    @Processor
    public final void reset(String latchName, MuleMessage muleMessage) throws CountDownLatchException {
        if (StringUtils.isBlank(latchName)) {
            String msg = "Latch name cannot be empty.";
            LOGGER.error(msg);
            throw new CountDownLatchException(msg);
        }
        final String fullLatchName = config.getApplicationName() + "-" + latchName;
        HazelcastManager hazelcastManager = this.muleContext.getRegistry().get(
                HAZELCAST_MANAGER_ID);
        if (null != hazelcastManager) {
            // we are in a clustered setup.
            LOGGER.info("_muleHazelcastManager found in Mule registry. "
                    + "Reset latch count of the cluster aware CountDownLatch.");
            HazelcastInstance hazelcastInstance = hazelcastManager.getHazelcastInstance();
            ICountDownLatch countDownLatch = hazelcastInstance
                    .getCountDownLatch(fullLatchName);
            if (null == countDownLatch) {
                String msg = "Couldnot find a pre existing Clustered ICountDownLatch with name -> '"
                        + fullLatchName + "'";
                LOGGER.error(msg);
                throw new CountDownLatchException(msg);
            } else {
                final int totalCount = countDownLatch.getCount();
                if (totalCount > 0) {
                    for (int index = 0; index < totalCount; index++) {
                        countDownInCluster(fullLatchName, hazelcastManager);
                    }
                }
            }
        } else {
            // single JVM stuff just use a standard latch and put it in registry
            LOGGER.info("_muleHazelcastManager was NOT FOUND in Mule registry. "
                    + "Reset latch count of the single JVM bound CountDownLatch.");
            MuleRegistry registry = this.muleContext.getRegistry();
            CountDownLatch countDownLatch = registry.get(fullLatchName);
            if (null == countDownLatch) {
                String msg = "Couldnot find Single JVM CountDownLatch with name '" + fullLatchName
                        + "' in registry for single JVM count mode.";
                LOGGER.error(msg);
                throw new CountDownLatchException(msg);
            } else {
                final long totalCount = countDownLatch.getCount();
                if (totalCount > 0) {
                    for (int index = 0; index < totalCount; index++) {
                        countDownInSingleJVM(fullLatchName);
                    }
                }
            }
        }
    }
    
    /**
     * Counts down on the latch which is represented by the {@code latchName}.
     * Assistant flows which are supposed to run concurrently to the main flow
     * should use this processor to clown down on the latch created in the main
     * flow.
     * <p>
     * This processor does not use {@link org.mule.util.lock.LockFactory} since
     * both {@link com.hazelcast.core.ICountDownLatch} and
     * {@link java.util.concurrent.CountDownLatch} internally use synchronizers
     * for thread and VM safety.
     * </p>
     * 
     * @param latchName
     *            - the name of the Latch on which to count down.
     * @param muleMessage
     *            - the optional mule message
     * @throws CountDownLatchException
     *             throws this exception if the latch referenced by the
     *             {@code latchName} is null or cannot be found.
     */
    @Processor
    public final void countDown(String latchName, MuleMessage muleMessage) throws CountDownLatchException {
        Object hazelcastManager = this.muleContext.getRegistry().get(HAZELCAST_MANAGER_ID);
        String fullLatchName = config.getApplicationName() + "-" + latchName;
        if (null != hazelcastManager) {
            // we are in a clustered setup.
            this.countDownInCluster(fullLatchName, (HazelcastManager) hazelcastManager);
        } else {
            //single JVM stuff
            this.countDownInSingleJVM(fullLatchName);
        }
    }
    
    /**
     * Makes the current flow processing thread to awaits on the latch. This
     * processor should be used in the main flow after all the worker tasks have
     * been created and delegated to worker VMs or flows.
     * <p>
     * This method blocks the main flow's processor thread form going ahead till
     * all the workers threads / flows have finished.
     * </p>
     * <p>
     * This processor does not use {@link org.mule.util.lock.LockFactory} since
     * both {@link com.hazelcast.core.ICountDownLatch} and
     * {@link java.util.concurrent.CountDownLatch} internally use synchronizers
     * for thread and VM safety.
     * </p>
     * 
     * @param latchName
     *            - the name of the Latch on which to count down.
     * @param muleMessage
     *            - the optional mule message
     * @throws CountDownLatchException
     *             throws this exception if: </p>
     *             <ul>
     *             <li>the latch referenced by the {@code latchName} is null or
     *             cannot be found OR</li>
     *             <li>an {@link InterruptedException} occurred while the latch
     *             was in the waiting state.
     *             </ul>
     *             </p>
     */
    @Processor
    public final void await(String latchName, MuleMessage muleMessage) throws CountDownLatchException {
        Object hazelcastManager = this.muleContext.getRegistry().get(HAZELCAST_MANAGER_ID);
        String fullLatchName = config.getApplicationName() + "-" + latchName;
        if (null != hazelcastManager) {
            // we are in a clustered setup.
            this.awaitInCluster(fullLatchName, (HazelcastManager) hazelcastManager);
        } else {
            //single JVM stuff
            this.awaitInSingleJVM(fullLatchName, (HazelcastManager) hazelcastManager);
        }
    }
    
    /**
     * Returns the current available count in the Latch.
     * 
     * <p>
     * This processor does not use {@link org.mule.util.lock.LockFactory} since
     * both {@link com.hazelcast.core.ICountDownLatch} and
     * {@link java.util.concurrent.CountDownLatch} internally use synchronizers
     * for thread and VM safety.
     * </p>
     * 
     * @param latchName
     *            - the name of the Latch on which to count down.
     * @param muleMessage
     *            - the optional mule message
     * @return the current available latch count value.
     * @throws CountDownLatchException
     *             throws this exception if the latch referenced by the
     *             {@code latchName} is null or cannot be found.
     */
    @Processor
    public final long getAvailableCounts(String latchName, MuleMessage muleMessage) throws CountDownLatchException {
        Object hazelcastManager = this.muleContext.getRegistry().get(HAZELCAST_MANAGER_ID);
        String fullLatchName = config.getApplicationName() + "-" + latchName;
        long availableCounts = 0;
        if (null != hazelcastManager) {
            availableCounts = this.getAvailableCountsInCluster(fullLatchName, (HazelcastManager) hazelcastManager);
        } else {
            availableCounts = this.getAvailableCountsInSingleJVM(fullLatchName, (HazelcastManager) hazelcastManager);
        }
        return availableCounts;
    }
    
    private long getAvailableCountsInSingleJVM(String fullLatchName, HazelcastManager hazelcastManager) 
                                                    throws CountDownLatchException {
        MuleRegistry registry = this.muleContext.getRegistry();
        CountDownLatch countDownLatch = registry.get(fullLatchName);
        if (null == countDownLatch) {
            String msg = "Couldnot find Single JVM CountDownLatch with name '" 
                            + fullLatchName + "' in registry.";
            LOGGER.error(msg);
            throw new CountDownLatchException(msg);
        } else {
            return countDownLatch.getCount();
        }
    }
    
    private long getAvailableCountsInCluster(String fullLatchName, HazelcastManager hazelcastManager) 
                                                throws CountDownLatchException {
        HazelcastInstance hazelcastInstance = hazelcastManager.getHazelcastInstance();
        ICountDownLatch countDownLatch = hazelcastInstance.getCountDownLatch(fullLatchName);
        if (null == countDownLatch) {
            String msg = "Couldnot find a pre existing Clustered ICountDownLatch with name -> '" 
                                + fullLatchName + "'";
            LOGGER.error(msg);
            throw new CountDownLatchException(msg);
        } else {
            return countDownLatch.getCount();
        }
    }
    
    private void awaitInCluster(String fullLatchName, HazelcastManager hazelcastManager) 
                                                throws CountDownLatchException {
        HazelcastInstance hazelcastInstance = hazelcastManager.getHazelcastInstance();
        ICountDownLatch countDownLatch = hazelcastInstance.getCountDownLatch(fullLatchName);
        if (null == countDownLatch) {
            String msg = "Couldnot find a pre existing Clustered ICountDownLatch with name -> '" 
                                + fullLatchName + "'";
            LOGGER.error(msg);
            throw new CountDownLatchException(msg);
        } else {
            try {
                long maxLatchAwaitTime = config.getMaxLatchAwaitTime();
                TimeUnit timeUnit = config.getTimeUnit();
                if (maxLatchAwaitTime == 0) {
                    maxLatchAwaitTime = 1;
                    timeUnit = TimeUnit.SECONDS;
                    LOGGER.info("Zero maxLatchAwaitTime was provided for Clustered ICountDownLatch with name -> '" 
                                    + fullLatchName + "' using 1 second instead.");
                } else {
                    LOGGER.info("Clustered ICountDownLatch with name -> '" + fullLatchName 
                                    + "' awaiting now for " + maxLatchAwaitTime
                                    + " " + timeUnit);
                }
                countDownLatch.await(maxLatchAwaitTime, timeUnit);
                
                LOGGER.info("Latch has now been opened for Clustered ICountDownLatch with name -> '" 
                                + fullLatchName + "'");
            } catch (InterruptedException e) {
                String msg = "Clustered ICountDownLatch with name -> '" + fullLatchName 
                                    + "' was interrupted while waiting.";
                LOGGER.error(msg);
                throw new CountDownLatchException(msg, e);
            }
        }
    }

    private void awaitInSingleJVM(String fullLatchName
                                        , HazelcastManager hazelcastManager) 
                                                throws CountDownLatchException {
        MuleRegistry registry = this.muleContext.getRegistry();
        CountDownLatch countDownLatch = registry.get(fullLatchName);
        if (null == countDownLatch) {
            String msg = "Couldnot find Single JVM CountDownLatch with name '" 
                            + fullLatchName + "' in registry.";
            LOGGER.error(msg);
            throw new CountDownLatchException(msg);
        } else {
            try {
                long maxLatchAwaitTime = config.getMaxLatchAwaitTime();
                TimeUnit timeUnit = config.getTimeUnit();
                if (maxLatchAwaitTime == 0) {
                    maxLatchAwaitTime = 1;
                    timeUnit = TimeUnit.SECONDS;
                    LOGGER.info("Zero maxLatchAwaitTime was provided for Single JVM CountDownLatch with name -> '" 
                                    + fullLatchName + "'. using 1 second instead.");
                } else {
                    LOGGER.info("Single JVM CountDownLatch with name -> '" + fullLatchName 
                                    + "' awaiting now for " + maxLatchAwaitTime
                                    + " " + timeUnit);
                }
                
                countDownLatch.await(maxLatchAwaitTime, timeUnit);
                
                LOGGER.info("Latch has now been opened for Single JVM CountDownLatch with name -> '" 
                                + fullLatchName + "'");
            } catch (InterruptedException e) {
                String msg = "Single JVM CountDownLatch with name -> '" + fullLatchName 
                                    + "' was interrupted while waiting.";
                LOGGER.error(msg);
                throw new CountDownLatchException(msg, e);
            }
        }
    }
    
    private void countDownInCluster(String fullLatchName
                                        , HazelcastManager hazelcastManager) 
                                                throws CountDownLatchException {
        HazelcastInstance hazelcastInstance = hazelcastManager.getHazelcastInstance();
        ICountDownLatch countDownLatch = hazelcastInstance.getCountDownLatch(fullLatchName);
        if (null == countDownLatch) {
            String msg = "Couldnot find a pre existing Clustered ICountDownLatch with name -> '" 
                                + fullLatchName + "'";
            LOGGER.error(msg);
            throw new CountDownLatchException(msg);
        } else {
            if (config.isLogLatchCountDowns()) {
                //if we  need to log the counts then we need a lock else 
                //heavy concurrency could give wrong values.
                Lock lock = muleContext.getLockFactory().createLock(config.getApplicationName());
                lock.lock();
                try {
                    countDownLatch.countDown();
                    if (config.isLogLatchCountDowns()) {
                        LOGGER.info("Count for Clustered ICountDownLatch with name '" + fullLatchName 
                                        + "' after 'count down' is " + countDownLatch.getCount());    
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                countDownLatch.countDown();
            }
        }
    }
    
    private void countDownInSingleJVM(String fullLatchName) throws CountDownLatchException {
        MuleRegistry registry = this.muleContext.getRegistry();
        CountDownLatch countDownLatch = registry.get(fullLatchName);
        if (null == countDownLatch) {
            String msg = "Couldnot find Single JVM CountDownLatch with name '" 
                            + fullLatchName + "' in registry for single JVM count mode.";
            LOGGER.error(msg);
            throw new CountDownLatchException(msg);
        } else {
            if (config.isLogLatchCountDowns()) {
                //if we  need to log the counts then we need a lock else 
                //heavy concurrency could give wrong values.
                Lock lock = muleContext.getLockFactory().createLock(config.getApplicationName());
                lock.lock();
                try {
                    countDownLatch.countDown();
                    //no need to put it back in registry as registry 
                    //is spring IOC container which returns objects by reference
                    if (config.isLogLatchCountDowns()) {
                        LOGGER.info("Count for Single JVM CountDownLatch with name '" + fullLatchName 
                                        + "' after 'count down' is " + countDownLatch.getCount());
                    }
                 } finally {
                    lock.unlock();
                }
            } else {
                countDownLatch.countDown();
            }
        }
    }
    
    private void createClusterAwareCountDownLatch(String fullLatchName, int totalCounts
                                                        , HazelcastManager hazelcastManager) 
                                                                throws CountDownLatchException {
        HazelcastInstance hazelcastInstance = hazelcastManager.getHazelcastInstance();
        ICountDownLatch countDownLatch = hazelcastInstance.getCountDownLatch(fullLatchName);
        if (countDownLatch.trySetCount(totalCounts)) {
            LOGGER.info("Clustered ICountDownLatch created with name '" + fullLatchName 
                            + "' and total counts value of " + totalCounts);
        } else {
            long availableCounts = countDownLatch.getCount();
            String msg = "Couldnot set total counts -> " + totalCounts 
                            + " on Clustered ICountDownLatch with name '" + fullLatchName + "'."
                            + " The latch alreayd has an available count value of " 
                            + availableCounts;
            LOGGER.error(msg);
            throw new CountDownLatchException(msg);
        }
    }
    
    private void createSingleJVMBoundCountDownLatch(String fullLatchName, int totalCounts) 
                                                        throws CountDownLatchException {
        CountDownLatch countDownLatch = new CountDownLatch(totalCounts);
        MuleRegistry registry = this.muleContext.getRegistry();
        try {
            registry.registerObject(fullLatchName, countDownLatch);
        } catch (RegistrationException e) {
            String msg = "Couldnot create Single JVM CountDownLatch with name '" 
                            + fullLatchName + "'";
            LOGGER.error(msg);
            throw new CountDownLatchException(msg, e);
        }
    }
    
    
    /**
     * @return the config
     */
    public final ConnectorConfig getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public final void setConfig(ConnectorConfig config) {
        this.config = config;
    }

    /**
     * @return the muleContext
     */
    public final MuleContext getMuleContext() {
        return muleContext;
    }

    /**
     * @param muleContext the muleContext to set
     */
    public void setMuleContext(MuleContext muleContext) { //NOSONAR
        this.muleContext = muleContext;
    }
}
