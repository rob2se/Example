package org.example.driver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.example.enums.Browsers;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.constants.Constants.APP_URL;

/**
 * @author Rob Thomson
 * @since 03/02/2023
 * @version 1.0
 *
 * TestBase class runs before and after each test.
 */
public class TestBase {
    /**
     * Logger instance to log messages.
     */
    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);

    /**
     * WebDriver instance used across tests.
     */
    private static WebDriver driver;

    /**
     * Launches the application by creating a new instance of a browser if it's not already created.
     * Then navigates to the login page.
     * If there's an error creating the browser instance, the exception message is logged and the test is interrupted.
     * If there's an error navigating to the login page, the exception message is logged and the test continues.
     */
    @Before
    public void launchApplication(Browsers browser){
        logger.info("Launching the application...");

        if (driver == null) {
            BrowserFactory bf = new BrowserFactory();
            try {
                driver = bf.createBrowserInstance(browser);
                DriverFactory.getInstance().setDriver(driver);
            } catch (Exception e) {
                logger.error("Error creating browser instance: " + e.getMessage());
                throw new TestInterruptedException("Error creating browser instance: " + e.getMessage(), e);
            }
        }
        try {
            driver.navigate().to(APP_URL);
        } catch (Exception e) {
            logger.error("Error navigating to the login page: " + e.getMessage());
        }
    }

    /**
     * Closes the browser instance and resets the driver to null.
     * If there's an error closing the browser instance, the exception message is logged.
     */
    @After
    public void tearDown(){
        logger.info("Closing the browser instance...");

        try {
            DriverFactory.getInstance().closeDriver();
        } catch (Exception e) {
            logger.error("Error closing browser instance: " + e.getMessage());
        }
        driver = null;
    }

    /**
     * Custom exception class for test interruption.
     * Extends the RuntimeException class.
     */
    private static class TestInterruptedException extends RuntimeException {
        /**
         * Constructor for the custom exception class TestInterruptedException.
         *
         * @param message a String that describes the error message
         * @param cause   the Throwable that caused the exception to be thrown
         */
        public TestInterruptedException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}