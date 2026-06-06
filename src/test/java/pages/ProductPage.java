package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By addToCartButton = By.cssSelector("button.btn.btn-add-to-cart");
    private final By preloaderOverlay = By.cssSelector("div.preloader");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void waitForOverlayToClear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(preloaderOverlay));
    }

    public void clickAddToCart() {
        waitForOverlayToClear();
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public boolean isProductPageDisplayed() {
        waitForOverlayToClear();
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        return driver.getCurrentUrl().contains("/p/9780316273992/the-age-of-ai-and-our-human-future");
    }
}