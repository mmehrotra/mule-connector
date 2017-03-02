
package com.ralphlauren.mule.modules.countdownlatch.automation.testcases;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ralphlauren.mule.modules.countdownlatch.CountDownLatchException;
import com.ralphlauren.mule.modules.countdownlatch.automation.AbstractTestCase;

public class CountDownTestCases extends AbstractTestCase {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountDownTestCases.class);
	
    @Test
    public void testCountDown() {
        String latchName = "test-latch-name";
    	try {
			this.getConnector().create(latchName, 14, null);
			this.startDummyWorkers(latchName);
			this.getConnector().await(latchName, null);
			Assert.assertEquals(0, this.getConnector().getAvailableCounts(latchName, null));
		} catch (CountDownLatchException e) {
			Assert.fail(e.getMessage());
		}
    }

    private void startDummyWorkers(final String latchName){
    	Runnable worker = new Runnable() {
			
			@Override
			public void run() {
				for(int i=0; i<14; i++) {
					try {
						getConnector().countDown(latchName, null);
						Thread.sleep(500); //sleep for 1/2 a second
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
