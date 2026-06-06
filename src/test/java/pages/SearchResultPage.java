package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstSearchResultLink = By.cssSelector("div.product-content.product-contents h3 a");
    private final By preloaderOverlay = By.cssSelector("div.preloader");

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void waitForOverlayToClear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(preloaderOverlay));
    }

    public void clickSearchResult() {
        waitForOverlayToClear();
        WebElement resultLink = wait.until(ExpectedConditions.elementToBeClickable(firstSearchResultLink));
        resultLink.click();
    }
}