package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartProductTitle = By.cssSelector("p.product-name.limit-lines a");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isCartPageDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductTitle));
        return driver.getCurrentUrl().contains("/checkout/cart");
    }

    public String getCartProductTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductTitle)).getText().trim();
    }
}
