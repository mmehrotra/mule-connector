package com.ralphlauren.mule.modules.correlation;

import java.util.concurrent.locks.Lock;

import javax.inject.Inject;

import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;
import com.mulesoft.mule.cluster.hazelcast.HazelcastManager;
import com.ralphlauren.mule.modules.correlation.config.ConnectorConfig;

/**
 * Mule Module to generate cluster safe correlation id from MuleMessage id. This
 * connector connects the mule message id with {@link IdGenerator} to produce a
 * cluster unique correlation id for messages which is derived from the
 * underlying mule message.
 * 
 * @author vwali
 *
 */
@Connector(name = "CorrelationId", friendlyName = "Mule Module CorrelationId"
            , minMuleVersion = "3.6", schemaVersion = "1.0"
            , description = "Mule Module to generate cluster safe CorrelationId for flows.")
public class MuleModuleCorrelationIdConnector {

    @Config
    private ConnectorConfig config;

    @Inject
    private MuleContext muleContext = null;

    private static final String HAZELCAST_MANAGER_ID = "_muleHazelcastManager";

    /**
     * Processor to add or update the Correlation id to the mule message.
     * 
     * @param muleMessage
     *            the {@link MuleMessage} to which correlation id needs to be
     *            added or updated.
     */
    @Processor
    public final void addOrUpdate(MuleMessage muleMessage) {
        Lock lock = muleContext.getLockFactory().createLock(config.getApplicationName());
        lock.lock();
        try {
            String correlationId = muleMessage.getUniqueId();
            Object hazelcastManager = this.muleContext.getRegistry().get(HAZELCAST_MANAGER_ID);
            if (null != hazelcastManager) {
                // we are in a clustered setup.
                HazelcastInstance hazelcastInstance = ((HazelcastManager) hazelcastManager).getHazelcastInstance();
                IdGenerator idGenerator = hazelcastInstance.getIdGenerator(config.getApplicationName());
                long newId = idGenerator.newId();
                correlationId += "-" + newId;
            }
            muleMessage.setCorrelationId(correlationId);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Processor to remove the correlation id from the mule message.
     * 
     * @param muleMessage
     *            the {@link MuleMessage} from which correlation id needs to be
     *            removed.
     */
    @Processor
    public final void remove(MuleMessage muleMessage) {
        Lock lock = muleContext.getLockFactory().createLock(
                config.getApplicationName());
        lock.lock();
        try {
            muleMessage.setCorrelationId(null);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @return the config
     */
    public final ConnectorConfig getConfig() {
        return config;
    }

    /**
     * @param config
     *            the config to set
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
     * @param muleContext
     *            the muleContext to set
     */
    public void setMuleContext(MuleContext muleContext) { //NOSONAR
        this.muleContext = muleContext;
    }
}
