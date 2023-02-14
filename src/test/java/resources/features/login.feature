Feature: Login Page
  As a user,
  I want to be able to log in to the application
  So that I can access the restricted pages.

  Scenario: Successful Login
    Given I am on the login page
    When I enter my valid username and password
    And I click the "Login" button
    Then I should be taken to the home page

  Scenario: Failed Login - Invalid Credentials
    Given I am on the login page
    When I enter an invalid username and password
    And I click the "Login" button
    Then I should see an error message that says "Invalid username or password"

  Scenario: Failed Login - Empty Fields
    Given I am on the login page
    When I leave the username and password fields empty
    And I click the "Login" button
    Then I should see an error message that says "Please enter a username and password"

  Scenario: Failed Login - SQL Injection
    Given I am on the login page
    When I enter a username of "' OR 1=1 --"
    And I enter a password of "test"
    And I click the "Login" button
    Then I should see an error message that says "Invalid username or password"

  Scenario: Failed Login - Cross-Site Scripting (XSS)
    Given I am on the login page
    When I enter a username of "<script>alert('XSS Attack')</script>"
    And I enter a password of "test"
    And I click the "Login" button
    Then I should not see the XSS attack code in the page source

  Scenario: Forgotten Password
    Given I am on the login page
    When I click the "Forgot Password" link
    And I enter my email address
    And I click the "Submit" button
    Then I should receive an email with a password reset link

  Scenario: Login Throttling
    Given I have entered an invalid username and password multiple times
    When I attempt to log in with a valid username and password
    Then I should see an error message that says "Your account is locked. Please try again later."