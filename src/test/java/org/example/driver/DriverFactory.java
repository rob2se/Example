package org.example.driver;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DriverFactory class implements the Singleton design pattern to store the WebDriver instance for each thread.
 */
public class DriverFactory {

    // Logger instance to log messages
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    // private constructor to prevent external instantiation
    private DriverFactory() {
    }

    // singleton instance of the DriverFactory
    private static final DriverFactory instance = new DriverFactory();

    /**
     * Get the instance of the DriverFactory.
     *
     * @return instance of DriverFactory.
     */
    public static DriverFactory getInstance() {
        return instance;
    }

    // ThreadLocal variable to store WebDriver instance for each thread
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Set the driver instance for the current thread.
     *
     * @param driverParam The WebDriver instance to set for the current thread.
     */
    public void setDriver(WebDriver driverParam) {
        // Log the message that the driver instance is being set for the current thread
        logger.debug("Setting the WebDriver instance for the current thread with driver [{}]", driverParam.toString());
        driver.set(driverParam);
    }

    /**
     * Get the driver instance for the current thread.
     *
     * @return The WebDriver instance for the current thread.
     * @throws IllegalStateException if WebDriver instance is not set for the current thread.
     */
    public WebDriver getDriver() {
        if(driver.get() == null) {
            // If the WebDriver instance is not set for the current thread, throw an exception
            logger.error("WebDriver instance is not set for the current thread");
            throw new IllegalStateException("WebDriver instance is not set for the current thread");
        }
        // Log the message that the driver instance is being retrieved for the current thread
        logger.debug("Getting the WebDriver instance for the current thread [{}]", driver.get().toString());
        return driver.get();
    }

    /**
     * Close the driver instance for the current thread.
     * @throws DriverClosingException if there is an error while closing the driver instance.
     */
    public void closeDriver() {
        try {
            // Log the message that the driver instance is being closed for the current thread
            logger.debug("Closing the WebDriver instance for the current thread [{}]", driver.get().toString());
            driver.get().close();
        } catch(Exception e) {
            // If there is an error while closing the driver instance, throw a custom exception
            logger.error("Error while closing the WebDriver instance: " + e.getMessage());
            throw new DriverClosingException("Error while closing the WebDriver instance: " + e.getMessage(), e);
        } finally {
            // Remove the ThreadLocal variable for the current thread
            driver.remove();
        }
    }

    /**
     * Custom exception class for the errors encountered while closing the driver instance.
     */
    private static class DriverClosingException extends RuntimeException {
        /**
         * Constructor for the custom exception class DriverClosingException.
         *
         * @param message a String that describes the error message
         * @param cause   the Throwable that caused the exception to be thrown
         */
        public DriverClosingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}