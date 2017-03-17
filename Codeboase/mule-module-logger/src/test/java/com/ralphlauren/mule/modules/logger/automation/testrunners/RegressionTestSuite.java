
package com.ralphlauren.mule.modules.logger.automation.testrunners;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

import com.ralphlauren.mule.modules.logger.MuleModuleLoggerConnector;
import com.ralphlauren.mule.modules.logger.automation.testcases.LogErrorTestCases;
import com.ralphlauren.mule.modules.logger.automation.testcases.LogMessageTestCases;

@RunWith(org.junit.experimental.categories.Categories.class)
@org.junit.runners.Suite.SuiteClasses({
	LogErrorTestCases.class,
    LogMessageTestCases.class
})
public class RegressionTestSuite {


    @BeforeClass
    public static void initialiseSuite() {
        ConnectorTestContext.initialize(MuleModuleLoggerConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() {
        ConnectorTestContext.shutDown();
    }

}
