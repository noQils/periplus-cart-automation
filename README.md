# Periplus Cart Automation

This repository contains a QA automation submission for Scenario Option B: Periplus Shopping Cart.

## Submission Scope

The implemented automated scenario covers the following flow:

1. Open `https://www.periplus.com/`
2. Sign in with a valid account
3. Search for a configured product
4. Open the matching product page from search results
5. Add the product to the cart
6. Open the cart page
7. Verify the expected product is present in the cart

The automated flow is implemented in [CartTest.java](./src/test/java/tests/CartTest.java). The QA documentation for this submission is available in [docs/qa-documentation.md](./docs/qa-documentation.md).

## Tech Stack

- Java 17
- Maven
- Selenium WebDriver
- TestNG
- WebDriverManager

## Framework Overview

This project uses a small, focused test automation framework:

- `testng.xml` runs the TestNG suite.
- `BaseTest` initializes Chrome, maximizes the browser window, opens the Periplus home page, and closes the driver after each test.
- Page Object Model classes under `src/test/java/pages` separate UI actions from test assertions.
- `ConfigReader` loads required test data from JVM properties, environment variables, or a local `.env` file.
- Explicit waits are used throughout the page objects to reduce timing-related instability.

## Project Structure

```text
periplus-cart-automation/
├── README.md
├── docs/
│   └── qa-documentation.md
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

The local `.env` file is already ignored by Git through `.gitignore`.

The same values can also be supplied through JVM properties:

- `periplus.email`
- `periplus.password`
- `periplus.product.name`

## How To Run

Run the full TestNG suite:

```bash
mvn clean test
```

You can also override configuration from the command line:

```bash
mvn clean test -Dperiplus.email=your_email@example.com -Dperiplus.password=your_password -Dperiplus.product.name="The Age of AI: And Our Human Future"
```

## What The Automated Test Verifies

The current implementation validates the following checkpoints:

- login succeeds and the My Account page is displayed
- the email shown on My Account matches the configured account
- the configured product can be selected from search results
- the product page opens successfully
- the browser page title matches the configured product name
- the cart page opens after adding the product
- the cart contains the configured product title

## Implementation Notes

- Search result selection uses a title-fragment match so the test can still select the intended book when Periplus truncates long result titles.
- Product verification uses the browser page title rather than the visible product-title element because it is more stable on the live site.
- Page objects wait for the Periplus preloader overlay to disappear before interacting with key elements.
- The cart navigation includes fallback handling for the notification modal that can intercept clicks after add-to-cart.
- The scenario is data-driven through configuration instead of hardcoded credentials or product values.

## Assumptions And Current Limitations

- This repository currently automates one happy-path scenario only.
- The test requires a valid Periplus account and a product that is searchable and available for add-to-cart.
- The cart verification checks whether any visible cart item title contains the configured product name.
- Pre-test cart cleanup is not automated, so if the same product already exists in the cart before execution, the test confirms presence in the cart but does not prove the cart changed from an empty state.
- The login and navigation checks rely on the current Periplus URL patterns and UI locators, which may need updates if the site changes.
- Selenium may print a Chrome DevTools Protocol warning depending on the installed Chrome version; the test can still pass when that warning appears.
