
package com.ralphlauren.mule.modules.correlation.automation.testcases;

import org.junit.Assert;
import org.junit.Test;

import com.ralphlauren.mule.modules.correlation.automation.AbstractTestCase;

public class AddOrUpdateTestCases extends AbstractTestCase {

    @Test
    public void testAddOrUpdate() {
    	this.getConnector().addOrUpdate(null);
    	Assert.assertTrue(true);
    }

}
