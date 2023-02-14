package org.example.helpers;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.example.driver.DriverFactory;

import org.example.enums.Locators;

/**
 * @author Rob Thomson
 * @since 03/02/2023
 * @version 1.0
 *
 * The class GetElements is used to get WebElements on a web page
 */
public class GetElements {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public GetElements() {
        // Get the web driver instance from the DriverFactory
        this.driver = DriverFactory.getInstance().getDriver();
        // Create a WebDriverWait instance with a timeout of 10 seconds
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for the page to load
        waitForPageLoad();
    }

    private void waitForPageLoad() {
        // Wait for the `document.readyState` to be equal to "complete"
        wait.until((ExpectedCondition<Boolean>) webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
        // Wait for jQuery to finish executing any active requests
        wait.until((ExpectedCondition<Boolean>) webDriver ->
                (Boolean)((JavascriptExecutor) webDriver).executeScript("return jQuery.active == 0")
        );
    }

    public WebElement getElement(Locators locator, String value) {
        // Get the By element based on the locator type and value
        By byElement = getByElement(locator, value);
        // Wait for the element to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
        // Wait for the element to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(byElement));
        // Return the found element
        return driver.findElement(byElement);
    }

    private By getByElement(Locators locator, String value) {
        // Switch on the locator type
        switch (locator) {
            case ClassName:
                return By.className(value);
            case CssSelector:
                return By.cssSelector(value);
            case Id:
                return By.id(value);
            case LinkText:
                return By.linkText(value);
            case Name:
                return By.name(value);
            case PartialLink:
                return By.partialLinkText(value);
            case TagName:
                return By.tagName(value);
            case Xpath:
                return By.xpath(value);
            default:
                // Throw an exception if the locator type is not recognized
                throw new IllegalArgumentException("Invalid locator type: " + locator);
        }
    }
}