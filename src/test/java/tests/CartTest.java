package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.Navbar;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.SearchResultPage;
import pages.ProductPage;
import pages.CartPage;
import utils.ConfigReader;

public class CartTest extends BaseTest {

    private final String testEmail = ConfigReader.getRequired("periplus.email");
    private final String testPassword = ConfigReader.getRequired("periplus.password");
    private final String productName = ConfigReader.getRequired("periplus.product.name");

    @Test
    public void addItemToCart() {
        // Step 1: Navigate to the homepage and click on Sign In, then log in with valid credentials
        Navbar navbar = new Navbar(driver);
        navbar.clickSignIn();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginCredentials(testEmail, testPassword);
        loginPage.clickLogin();

        MyAccountPage myAccountPage = new MyAccountPage(driver);

        Assert.assertTrue(myAccountPage.isMyAccountPageDisplayed(), 
        "My Account page should be displayed after successful login.");
        Assert.assertEquals(myAccountPage.getAccountEmail(), testEmail,
        "The email displayed on My Account page should match the logged in email.");

        // Step 2: Search for a specific product and navigate to its product page
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        ProductPage productPage = new ProductPage(driver);

        navbar.searchForProduct(productName);
        searchResultPage.clickSearchResult();

        Assert.assertTrue(productPage.isProductPageDisplayed(),
        "Product page should be displayed after clicking on the search result product.");

        // Step 3: Add the product to the cart and verify that it has been added successfully
        productPage.clickAddToCart();
        navbar.clickCartIcon();

        CartPage cartPage = new CartPage(driver);

        Assert.assertTrue(cartPage.isCartPageDisplayed(),
        "Cart page should be displayed after adding an item to the cart.");
        Assert.assertTrue(cartPage.isCorrectProductDisplayedInCart(),
        "The correct product should be displayed in the cart.");
    }
}