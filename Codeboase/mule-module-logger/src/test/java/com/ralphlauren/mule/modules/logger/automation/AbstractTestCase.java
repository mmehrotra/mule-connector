
package com.ralphlauren.mule.modules.logger.automation;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.expression.ExpressionManager;
import org.mule.tools.devkit.ctf.mockup.ConnectorDispatcher;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

import com.ralphlauren.mule.modules.logger.MuleModuleLoggerConnector;
import com.ralphlauren.mule.modules.logger.config.ConnectorConfig;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractTestCase {
    
    @Mock
    private ConnectorConfig mockedConfig;    
    @Mock
    private MuleContext mockedMuleContext;    
    @Mock
    private MuleEvent mockedMuleEvent;    
    @Mock
    private ExpressionManager mockedExpressionManager;
    @Mock
    private MuleMessage mockedMuleMessage;
    
    private MuleModuleLoggerConnector connector;
    private ConnectorDispatcher<MuleModuleLoggerConnector> dispatcher;

    private MuleEvent muleEvent;
    private ExpressionManager expressionManager;
    private MuleMessage muleMessage;
    
    protected MuleModuleLoggerConnector getConnector() {
        return connector;
    }

    protected ConnectorDispatcher<MuleModuleLoggerConnector> getDispatcher() {
        return dispatcher;
    }

    @Before
    public void init() {
        //Initialization for single-test run
        ConnectorTestContext.initialize(MuleModuleLoggerConnector.class, false);
        //Context instance
        ConnectorTestContext<MuleModuleLoggerConnector> context = ConnectorTestContext.getInstance();
        //Connector dispatcher   
        dispatcher = context.getConnectorDispatcher();
        connector = dispatcher.createMockup();        
        connector.setConfig(mockedConfig);
        connector.setExpressionManager(mockedExpressionManager);        
        this.muleEvent = mockedMuleEvent;
        this.muleMessage = mockedMuleMessage;
        
    }

    @After
    public void shutDown() {
        ConnectorTestContext.shutDown(false);
    }

    /**
     * @param connector the connector to set
     */
    public final void setConnector(MuleModuleLoggerConnector connector) {
        this.connector = connector;
    }

    /**
     * @return the muleEvent
     */
    public final MuleEvent getMuleEvent() {
        return muleEvent;
    }

    /**
     * @param muleEvent the muleEvent to set
     */
    public final void setMuleEvent(MuleEvent muleEvent) {
        this.muleEvent = muleEvent;
    }

    /**
     * @return the expressionManager
     */
    public final ExpressionManager getExpressionManager() {
        return expressionManager;
    }

    /**
     * @param expressionManager the expressionManager to set
     */
    public final void setExpressionManager(ExpressionManager expressionManager) {
        this.expressionManager = expressionManager;
    }

    /**
     * @return the muleMessage
     */
    public final MuleMessage getMuleMessage() {
        return muleMessage;
    }

    /**
     * @param muleMessage the muleMessage to set
     */
    public final void setMuleMessage(MuleMessage muleMessage) {
        this.muleMessage = muleMessage;
    }


}
