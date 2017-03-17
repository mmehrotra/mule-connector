
package com.ralphlauren.mule.modules.correlation.automation.testrunners;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

import com.ralphlauren.mule.modules.correlation.MuleModuleCorrelationIdConnector;
import com.ralphlauren.mule.modules.correlation.automation.testcases.AddOrUpdateTestCases;
import com.ralphlauren.mule.modules.correlation.automation.testcases.RemoveTestCases;

@RunWith(org.junit.experimental.categories.Categories.class)
@org.junit.runners.Suite.SuiteClasses({
    AddOrUpdateTestCases.class,
    RemoveTestCases.class
})
public class RegressionTestSuite {


    @BeforeClass
    public static void initialiseSuite() {
        ConnectorTestContext.initialize(MuleModuleCorrelationIdConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() {
        ConnectorTestContext.shutDown();
    }

}
