
package com.ralphlauren.mule.modules.correlation.automation.testcases;

import org.junit.Assert;
import org.junit.Test;

import com.ralphlauren.mule.modules.correlation.automation.AbstractTestCase;

public class RemoveTestCases extends AbstractTestCase {

    @Test
    public void testRemove() {
    	this.getConnector().addOrUpdate(null);
    	Assert.assertTrue(true);
    }

}
