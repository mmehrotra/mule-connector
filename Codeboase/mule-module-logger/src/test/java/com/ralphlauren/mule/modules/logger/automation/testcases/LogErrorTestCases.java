
package com.ralphlauren.mule.modules.logger.automation.testcases;

import junit.framework.Assert;

import org.junit.Test;

import com.ralphlauren.mule.modules.logger.automation.AbstractTestCase;

public class LogErrorTestCases extends AbstractTestCase {

    @Test
    public void testLogError() {
    	this.getConnector().logError("Test ERROR Message", "DSB1003", "Dummy test Process", false, true, getMuleMessage(), getMuleEvent());
    	Assert.assertTrue(true);
    }
}
