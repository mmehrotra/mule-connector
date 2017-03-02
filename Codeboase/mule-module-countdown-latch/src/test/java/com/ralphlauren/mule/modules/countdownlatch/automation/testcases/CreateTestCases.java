
package com.ralphlauren.mule.modules.countdownlatch.automation.testcases;

import junit.framework.Assert;

import org.junit.Test;

import com.ralphlauren.mule.modules.countdownlatch.CountDownLatchException;
import com.ralphlauren.mule.modules.countdownlatch.MuleModuleCountdownLatchConnector;
import com.ralphlauren.mule.modules.countdownlatch.automation.AbstractTestCase;

public class CreateTestCases extends AbstractTestCase {

    @Test
    public void testCreate() {
    	String latchName = "test-latch-name";
        MuleModuleCountdownLatchConnector connector = this.getConnector();
        try {
			connector.create(latchName, 12, null);
			long availableCounts = connector.getAvailableCounts(latchName, null);
			Assert.assertEquals(12, availableCounts);
		} catch (CountDownLatchException e) {
			Assert.fail(e.getMessage());
		}
    }
    
    @Test(expected=CountDownLatchException.class)
    public void testCreateNegativeTotalCounts() throws CountDownLatchException {
    	String latchName = "test-latch-name";
        this.getConnector().create(latchName, -2, null);
    }
    
    @Test(expected=CountDownLatchException.class)
    public void testCreateEmpgtyLatchName() throws CountDownLatchException {
    	String latchName = "";
    	this.getConnector().create(latchName, 2, null);
    }
}
