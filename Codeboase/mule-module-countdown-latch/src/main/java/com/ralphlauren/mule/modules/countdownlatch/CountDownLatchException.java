package com.ralphlauren.mule.modules.countdownlatch;

/**
 * Mule Module Countdown Latch main Exception type. Latch Operations throw this
 * exception to indicate failures on Latch processors.
 * <p>
 * App flows should catch this exception type and handle it accordingly. This is
 * important because a normal usecase would want to decrement the latch count in
 * the main flows or in the exception strategy blocks if an exception happened
 * in the flow. But if the exception happened because of this module then the
 * latch may get decremented twice which is not right.
 * </p>
 * 
 * @author vwali
 *
 */
public class CountDownLatchException extends Exception {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = -8649607856800503125L;

    /**
     * Default Constructor with no parameters
     */
    public CountDownLatchException() {
        super();
    }

    /**
     * Overloaded Constructor with parameters
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CountDownLatchException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Overloaded Constructor with parameters
     * @param message
     * @param cause
     */
    public CountDownLatchException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Overloaded Constructor with parameters
     * @param message
     */
    public CountDownLatchException(String message) {
        super(message);
    }

    /**
     * Overloaded Constructor with parameters
     * @param cause
     */
    public CountDownLatchException(Throwable cause) {
        super(cause);
    }
}
