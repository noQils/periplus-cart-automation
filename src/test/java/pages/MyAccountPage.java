package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By accountEmailValue = By.xpath("//tr[td/strong[text()='E-mail']]/td[3]");

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isMyAccountPageDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountEmailValue));
        return driver.getCurrentUrl().contains("/account/Your-Account");
    }

    public String getAccountEmail() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accountEmailValue)).getText().trim();
    }
}