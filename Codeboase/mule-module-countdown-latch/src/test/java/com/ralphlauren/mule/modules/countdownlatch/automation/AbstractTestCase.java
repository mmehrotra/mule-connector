
package com.ralphlauren.mule.modules.countdownlatch.automation;

import org.junit.After;
import org.junit.Before;
import org.mule.tools.devkit.ctf.mockup.ConnectorDispatcher;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

import com.ralphlauren.mule.modules.countdownlatch.MuleModuleCountdownLatchConnector;

public abstract class AbstractTestCase {

    private MuleModuleCountdownLatchConnector connector;
    private ConnectorDispatcher<MuleModuleCountdownLatchConnector> dispatcher;

    protected MuleModuleCountdownLatchConnector getConnector() {
        return connector;
    }

    protected ConnectorDispatcher<MuleModuleCountdownLatchConnector> getDispatcher() {
        return dispatcher;
    }

    @Before
    public void init() {
        //Initialization for single-test run
        ConnectorTestContext.initialize(MuleModuleCountdownLatchConnector.class, false);
        //Context instance
        ConnectorTestContext<MuleModuleCountdownLatchConnector> context = ConnectorTestContext.getInstance();
        //Connector dispatcher
        dispatcher = context.getConnectorDispatcher();
        connector = dispatcher.createMockup();
    }

    @After
    public void shutDown() {
        ConnectorTestContext.shutDown(false);
    }

}
