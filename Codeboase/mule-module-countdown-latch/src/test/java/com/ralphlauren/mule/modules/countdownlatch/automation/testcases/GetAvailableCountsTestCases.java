
package com.ralphlauren.mule.modules.countdownlatch.automation.testcases;

import junit.framework.Assert;

import org.junit.Test;

import com.ralphlauren.mule.modules.countdownlatch.CountDownLatchException;
import com.ralphlauren.mule.modules.countdownlatch.MuleModuleCountdownLatchConnector;
import com.ralphlauren.mule.modules.countdownlatch.automation.AbstractTestCase;

public class GetAvailableCountsTestCases extends AbstractTestCase {

    @Test
    public void testGetAvailableCounts() {
    	String latchName = "test-latch-name";
        MuleModuleCountdownLatchConnector connector = this.getConnector();
        try {
			connector.create(latchName, 10, null);
			long availableCounts = connector.getAvailableCounts(latchName, null);
			Assert.assertEquals(10, availableCounts);
		} catch (CountDownLatchException e) {
			Assert.fail(e.getMessage());
		}
    }
}
