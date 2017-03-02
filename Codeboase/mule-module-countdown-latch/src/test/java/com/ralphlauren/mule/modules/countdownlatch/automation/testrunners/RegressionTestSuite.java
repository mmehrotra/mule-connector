
package com.ralphlauren.mule.modules.countdownlatch.automation.testrunners;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

import com.ralphlauren.mule.modules.countdownlatch.MuleModuleCountdownLatchConnector;
import com.ralphlauren.mule.modules.countdownlatch.automation.testcases.AwaitTestCases;
import com.ralphlauren.mule.modules.countdownlatch.automation.testcases.CountDownTestCases;
import com.ralphlauren.mule.modules.countdownlatch.automation.testcases.CreateTestCases;

@RunWith(org.junit.experimental.categories.Categories.class)
@org.junit.runners.Suite.SuiteClasses({
    CreateTestCases.class,
    CountDownTestCases.class,
    AwaitTestCases.class
})
public class RegressionTestSuite {

    @BeforeClass
    public static void initialiseSuite() {
        ConnectorTestContext.initialize(MuleModuleCountdownLatchConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() {
        ConnectorTestContext.shutDown();
    }
}
