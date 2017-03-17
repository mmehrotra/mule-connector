
package com.ralphlauren.mule.modules.correlation.automation;

import org.junit.After;
import org.junit.Before;
import org.mule.tools.devkit.ctf.mockup.ConnectorDispatcher;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

import com.ralphlauren.mule.modules.correlation.MuleModuleCorrelationIdConnector;

public abstract class AbstractTestCase {

    private MuleModuleCorrelationIdConnector connector;
    private ConnectorDispatcher<MuleModuleCorrelationIdConnector> dispatcher;

    protected MuleModuleCorrelationIdConnector getConnector() {
        return connector;
    }

    protected ConnectorDispatcher<MuleModuleCorrelationIdConnector> getDispatcher() {
        return dispatcher;
    }

    @Before
    public void init() {
        //Initialization for single-test run
        ConnectorTestContext.initialize(MuleModuleCorrelationIdConnector.class, false);
        //Context instance
        ConnectorTestContext<MuleModuleCorrelationIdConnector> context = ConnectorTestContext.getInstance();
        //Connector dispatcher
        dispatcher = context.getConnectorDispatcher();
        connector = dispatcher.createMockup();
    }

    @After
    public void shutDown() {
        ConnectorTestContext.shutDown(false);
    }

}
