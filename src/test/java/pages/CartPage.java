package pages;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartProductTitles = By.cssSelector("p.product-name.limit-lines a");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isCartPageDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductTitles));
        return driver.getCurrentUrl().contains("/checkout/cart");
    }

    public boolean containsProductTitle(String expectedProductTitle) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductTitles));

        String normalizedExpectedTitle = expectedProductTitle.trim().toLowerCase(Locale.ROOT);
        List<WebElement> productTitles = driver.findElements(cartProductTitles);

        for (WebElement productTitle : productTitles) {
            String normalizedActualTitle = productTitle.getText().trim().toLowerCase(Locale.ROOT);
            if (normalizedActualTitle.contains(normalizedExpectedTitle)) {
                return true;
            }
        }

        return false;
    }
}
