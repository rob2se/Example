package org.example.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.enums.Browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The BrowserFactory class is responsible for creating instances of WebDriver
 * based on the browser selected in the test case.
 */
public class BrowserFactory {
    /**
     * Logger instance to log messages.
     */
    private static final Logger logger = LoggerFactory.getLogger(BrowserFactory.class);

    private final ChromeOptions chromeOptions;
    private final FirefoxOptions firefoxOptions;
    private final InternetExplorerOptions internetExplorerOptions;

    /**
     * Constructor that initializes the options for each browser.
     */
    public BrowserFactory() {
        // Configure Chrome options
        chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        chromeOptions.addArguments("--start-fullscreen");

        // Configure Firefox options
        firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("-private");

        // Configure Internet Explorer options
        internetExplorerOptions = new InternetExplorerOptions();
        internetExplorerOptions.addCommandSwitches("-private");

        logger.info("Browser options have been initialized");
    }

    /**
     * This method creates a new WebDriver instance based on the given browser type.
     *
     * @param browser The browser selected in the test case.
     * @return A new instance of the required WebDriver.
     * @throws IllegalArgumentException If the input `browser` is not valid.
     */
    public WebDriver createBrowserInstance(Browsers browser) {
        WebDriver driver = null;

        logger.info("Creating instance of browser: " + browser);

        switch (browser) {
            case Chrome:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
                break;
            case Firefox:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case IE:
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver(internetExplorerOptions);
                break;
            default:
                logger.error("Invalid browser type: " + browser);
                throw new IllegalArgumentException("Invalid browser type: " + browser);
        }
        logger.info("Instance of browser created: " + browser);
        return driver;
    }
}