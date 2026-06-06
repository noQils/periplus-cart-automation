package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Navbar {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By signInButton = By.cssSelector("#nav-signin-text a");
    private final By searchInput = By.id("filter_name_desktop");
    private final By searchButton = By.cssSelector("button.btnn[type='submit']");
    private final By cartLink = By.cssSelector("#show-your-cart a");
    private final By preloaderOverlay = By.cssSelector("div.preloader");
    private final By notificationModal = By.id("Notification-Modal");

    public Navbar(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void waitForOverlayToClear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(preloaderOverlay));
    }

    public void clickSignIn() {
        waitForOverlayToClear();
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }

    public void searchForProduct(String productName) {
        waitForOverlayToClear();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(productName);
        waitForOverlayToClear();
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void clickCartIcon() {
        waitForOverlayToClear();
        WebElement cart = wait.until(ExpectedConditions.presenceOfElementLocated(cartLink));
        String cartUrl = cart.getAttribute("href");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        } catch (ElementClickInterceptedException exception) {
            List<WebElement> modals = driver.findElements(notificationModal);
            if (!modals.isEmpty() && modals.get(0).isDisplayed()) {
                driver.navigate().to(cartUrl);
                return;
            }
            throw exception;
        }
    }
}