
package com.ralphlauren.mule.modules.countdownlatch.automation.testcases;

import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ralphlauren.mule.modules.countdownlatch.CountDownLatchException;
import com.ralphlauren.mule.modules.countdownlatch.automation.AbstractTestCase;

public class AwaitTestCases extends AbstractTestCase {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AwaitTestCases.class);
	
    @Test
    public void testAwait() {
    	String latchName = "test-latch-name";
    	try {
			this.getConnector().create(latchName, 10, null);
			this.startDummyWorkers(latchName);
			this.getConnector().await(latchName, null);
			Assert.assertTrue(true);
		} catch (CountDownLatchException e) {
			Assert.fail(e.getMessage());
		}
    }

    private void startDummyWorkers(final String latchName){
    	Runnable worker = new Runnable() {
			
			@Override
			public void run() {
				for(int i=0; i<10; i++) {
					try {
						getConnector().countDown(latchName, null);
						Thread.sleep(1000); //sleep for 1 second
						LOGGER.info("Count down done in startDummyWorkers Runnable.");
					} catch (CountDownLatchException | InterruptedException e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}
		};
		
		//start the worker 
		(new Thread(worker)).start();
    }
}
