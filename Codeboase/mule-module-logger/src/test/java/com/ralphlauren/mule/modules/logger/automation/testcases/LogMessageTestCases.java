
package com.ralphlauren.mule.modules.logger.automation.testcases;

import org.junit.Assert;
import org.junit.Test;

import com.ralphlauren.mule.modules.logger.LogLevelEnum;
import com.ralphlauren.mule.modules.logger.automation.AbstractTestCase;

public class LogMessageTestCases extends AbstractTestCase {
	
    @Test
    public void testLogDebugMessage() {
        this.getConnector().logMessage(LogLevelEnum.DEBUG, "Test DEBUG Message.", 
        		"Dummy test Process", false, getMuleMessage(), getMuleEvent());
        Assert.assertTrue(true);
    }
    
    @Test
    public void testLogInfoMessage() {
        this.getConnector().logMessage(LogLevelEnum.INFO, "Test INFO Message.", 
        		"Dummy test Process", false, getMuleMessage(), getMuleEvent());
        Assert.assertTrue(true);
    }
    
    @Test
    public void testLogWarnMessage() {
        this.getConnector().logMessage(LogLevelEnum.WARN, "Test WARN Message.", 
        		"Dummy test Process", false, getMuleMessage(), getMuleEvent());
        Assert.assertTrue(true);
    }
    
    @Test
    public void testLogTraceMessage() {
        this.getConnector().logMessage(LogLevelEnum.TRACE, "Test TRACE Message.", 
        		"Dummy test Process", false, getMuleMessage(), getMuleEvent());
        Assert.assertTrue(true);
    }
    
    @Test
    public void testLogDebugMessageWithCustomProcessType() {
        this.getConnector().logMessage(LogLevelEnum.DEBUG, "Test DEBUG Message."
        									, "Dummy test Process", false, getMuleMessage(), getMuleEvent());
        Assert.assertTrue(true);
    }
    
    @Test
    public void testLogInfoMessageWithCustomProcessType() {
        this.getConnector().logMessage(LogLevelEnum.INFO, "Test INFO Message."
											, "Dummy test Process", false, getMuleMessage(), getMuleEvent());
        Assert.assertTrue(true);
    }
    
    @Test
    public void testLogWarnMessageWithCustomProcessType() {
        this.getConnector().logMessage(LogLevelEnum.WARN, "Test WARN Message."
											, "Dummy test Process", false, getMuleMessage(), getMuleEvent());
        Assert.assertTrue(true);
    }
    
    @Test
    public void testLogTraceMessageWithCustomProcessType() {
        this.getConnector().logMessage(LogLevelEnum.TRACE, "Test TRACE Message."
											, "Dummy test Process", false, getMuleMessage(), getMuleEvent());
        Assert.assertTrue(true);
    }
}
