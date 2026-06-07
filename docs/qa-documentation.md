# Quality Assurance Documentation

This document is the QA documentation artifact for the Periplus shopping cart automation submission. It is based on the actual implementation in this repository and documents both the automated flow and additional baseline coverage for the shopping cart feature.

## Purpose And Scope

The purpose of this document is to define a clear, reproducible, and traceable set of test cases for validating the shopping cart workflow on the Periplus website.

Current scope:

- sign in with a valid customer account
- search for a product
- open the corresponding product page
- add one product to the cart
- verify the product appears in the cart

Current out-of-scope areas:

- checkout and payment
- promotions, vouchers, and pricing rules
- guest checkout
- cross-browser execution
- mobile-specific UI behavior

## System Under Test

- Application: Periplus web store
- Base URL: `https://www.periplus.com/`
- Feature under test: shopping cart add-item flow
- Automation entry point: [CartTest.java](../src/test/java/tests/CartTest.java)

The automated scenario uses these page objects:

- `Navbar`
- `LoginPage`
- `MyAccountPage`
- `SearchResultPage`
- `ProductPage`
- `CartPage`

## Test Strategy

The current implementation uses a focused happy-path strategy:

- one end-to-end UI scenario is automated with Selenium WebDriver and TestNG
- the scenario uses real user interaction through Chrome
- test data is externalized through configuration
- assertions are placed on key functional checkpoints rather than on cosmetic UI details

Design choices reflected in the implementation:

- explicit waits are used to handle asynchronous page behavior
- the search-result selector uses a title fragment to tolerate long-title truncation
- product verification uses the browser title as the most stable value on the live site
- cart access includes fallback handling for the add-to-cart notification modal

## Environment And Test Data

### Environment

- Operating mode: local execution
- Browser: Google Chrome
- Language/runtime: Java 17
- Build tool: Maven
- Test framework: TestNG
- Driver management: WebDriverManager

### Configuration Sources

The framework reads required data in the following order:

1. JVM properties
2. Environment variables
3. `.env` file in the project root

### Required Test Data

- `PERIPLUS_EMAIL` or `periplus.email`
- `PERIPLUS_PASSWORD` or `periplus.password`
- `PERIPLUS_PRODUCT_NAME` or `periplus.product.name`

Example:

```env
PERIPLUS_EMAIL=your_email@example.com
PERIPLUS_PASSWORD=your_password
PERIPLUS_PRODUCT_NAME=The Age of AI: And Our Human Future
```

## Entry Assumptions And Preconditions

The current automation assumes the following before execution starts:

- the Periplus website is reachable
- the test account is registered and can log in successfully
- the configured product exists in search results
- the configured product can be opened and added to the cart
- the configured product name can be identified in at least one visible cart item after add-to-cart

Pre-test cart cleanup is not automated. If the same product already exists in the cart before execution, the current test still proves product presence after the flow, but not a clean cart-state transition.

## What A Well-Constructed Test Case Should Contain

For this project, each useful test case should define:

- a unique test case ID for traceability
- a clear title and objective
- preconditions and test data
- ordered execution steps
- expected results that can be verified objectively
- postconditions
- execution mode, such as automated now or manual candidate

This structure keeps the test documentation reproducible and directly relatable to the automation code.

## Detailed Automated Test Case

### TC-CART-001

| Field | Value |
| --- | --- |
| Test Case ID | `TC-CART-001` |
| Title | Add one product to cart from search results |
| Objective | Verify that a signed-in user can search for a configured product, open its details page, add it to the cart, and confirm the expected product is shown in the cart |
| Priority | High |
| Execution Mode | Automated now |
| Traceability | `CartTest.addItemToCart()` in [CartTest.java](../src/test/java/tests/CartTest.java) |

#### Preconditions

- A valid Periplus account is available.
- The configured product is searchable on Periplus.
- The configured product can be added to the cart.
- The account cart is in a predictable state for first-item validation.

#### Test Data

- Email: configured test account
- Password: configured test account password
- Product name: configured value from `periplus.product.name`

#### Steps And Expected Results

| Step | Action | Expected Result |
| --- | --- | --- |
| 1 | Open `https://www.periplus.com/` | Home page opens successfully |
| 2 | Click `Sign In` from the navigation bar | Login page is displayed |
| 3 | Enter valid email and password | Credential fields accept the values |
| 4 | Submit the login form | User is redirected to My Account |
| 5 | Verify My Account page | The page URL contains `/account/Your-Account` and the account email is visible |
| 6 | Search for the configured product | Search results page loads for the requested item |
| 7 | Open the matching product result | Product details page opens |
| 8 | Verify product page | Add-to-cart button is available and the browser title matches the configured product name |
| 9 | Click `Add to Cart` | Product is added without blocking errors |
| 10 | Open the cart | Cart page loads successfully |
| 11 | Verify cart contents | At least one visible cart item title contains the configured product name |

#### Postconditions

- The selected product remains in the cart after the test.

## Additional Shopping Cart Coverage

The table below lists relevant baseline coverage beyond the currently automated scenario. These cases are useful for manual validation now and for future automation expansion later.

| ID | Test Case | Type | Priority | Expected Result | Status |
| --- | --- | --- | --- | --- | --- |
| TC-CART-001 | Add one product to cart from search results | Positive | High | Product is added and shown in the cart | Automated now |
| TC-CART-002 | Add one product to cart directly from the product details page | Positive | High | Product is added and shown in the cart | Manual candidate |
| TC-CART-003 | Verify cart badge or cart count after adding one product | Positive | Medium | Cart counter updates correctly | Manual candidate |
| TC-CART-004 | Add the same product again and verify quantity behavior | Positive | High | Quantity behavior matches site rules | Manual candidate |
| TC-CART-005 | Remove a product from the cart | Positive | High | Product is removed and cart state refreshes correctly | Manual candidate |
| TC-CART-006 | Update cart quantity to a valid number | Positive | High | Quantity and totals refresh correctly | Manual candidate |
| TC-CART-007 | Attempt to add an unavailable or out-of-stock product | Negative | High | System blocks the action or shows a proper message | Manual candidate |
| TC-CART-008 | Enter an invalid quantity value in the cart | Negative | Medium | Invalid input is rejected or normalized according to site behavior | Manual candidate |
| TC-CART-009 | Refresh the cart page after adding a product | Reliability | Medium | The cart still shows the same product after refresh | Manual candidate |
| TC-CART-010 | Log out and log back in after adding a product | Reliability | Medium | Cart persistence matches the expected account behavior | Manual candidate |
| TC-CART-011 | Add multiple different products to the cart | Positive | Medium | All selected products appear correctly in the cart | Manual candidate |
| TC-CART-012 | Start from a non-empty cart and add a new product | Reliability | High | Newly added product is still identifiable and cart behavior remains correct | Manual candidate |

## Risks And Limitations

- The automation depends on a real Periplus account, so credential issues can block execution.
- Product availability or search-result ordering can change over time and affect reproducibility.
- The search-result locator intentionally uses a partial-title strategy, which is robust for truncation but still depends on current DOM structure.
- Product verification relies on the browser page title format remaining stable.
- The cart verification checks whether any visible cart item contains the configured product name, so repeated runs can still pass if that same product was already present in the cart before the test started.
- UI changes, overlays, or modal behavior changes on the Periplus site may require locator or wait updates.
