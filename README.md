# Periplus Cart Automation

UI automation project for the Periplus shopping cart workflow using Java, Selenium WebDriver, Maven, and TestNG.

## Scope

This project automates the happy-path scenario for option B:

1. Open `https://www.periplus.com/`
2. Sign in with a valid account
3. Search for a product
4. Open the product page
5. Add the product to the cart
6. Verify the cart page is displayed
7. Verify the expected product exists in the cart

The current automated test is implemented in [CartTest.java](./src/test/java/tests/CartTest.java).

## Tech Stack

- Java 17
- Maven
- Selenium WebDriver
- TestNG
- WebDriverManager

## Project Structure

```text
periplus-cart-automation/
├── README.md
├── pom.xml
├── testng.xml
└── src/
    └── test/
        └── java/
            ├── base/
            │   └── BaseTest.java
            ├── pages/
            │   ├── CartPage.java
            │   ├── LoginPage.java
            │   ├── MyAccountPage.java
            │   ├── Navbar.java
            │   ├── ProductPage.java
            │   └── SearchResultPage.java
            ├── tests/
            │   └── CartTest.java
            └── utils/
                └── ConfigReader.java
```

## Prerequisites

Make sure the following are installed:

- Java 17
- Maven
- Google Chrome

## Configuration

The test reads configuration in this order:

1. JVM properties
2. Environment variables
3. `.env` file in the project root

Create a `.env` file in the project root with values like:

```env
PERIPLUS_EMAIL=your_email@example.com
PERIPLUS_PASSWORD=your_password
PERIPLUS_PRODUCT_NAME=The Age of AI: And Our Human Future
```

## How To Run

Run the full TestNG suite:

```bash
mvn clean test
```

You can also override values from the command line:

```bash
mvn clean test -Dperiplus.email=your_email@example.com -Dperiplus.password=your_password -Dperiplus.product.name="The Age of AI: And Our Human Future"
```

## Assertions Covered

The current test verifies:

- login succeeds and the My Account page is displayed
- the logged-in email matches the expected account
- the product page opens from search results
- the cart page opens after add-to-cart
- the expected product is present in the cart

## Notes

- The test uses Page Object Model classes under `src/test/java/pages`.
- `ConfigReader` supports loading config from `.env`, JVM properties, or environment variables.
- Periplus uses loading overlays and a notification modal during the flow, so the page objects include explicit waits to keep the test stable.

## Known Limitations

- This project currently focuses on one happy-path scenario.
- The selected product flow is tuned to the current Periplus UI and may need locator updates if the site changes.
- Selenium may print a Chrome DevTools Protocol version warning depending on the installed Chrome version. The test can still pass with that warning.
