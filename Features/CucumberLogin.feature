Feature: CucumberLogin: Login with Valid credentials

  @sanity
  Scenario: Positive LogIn test
    Given user Launch browser
    And opens page "https://practicetestautomation.com/practice-test-login/"
    When user enters a username "student" in username field
    And user enters a password "Password123" in password field
    And punch Submit button
    Then verify new page URL contains "practicetestautomation.com/logged-in-successfully/"
    And verify new page contains expected text "Congratulations" or "successfully logged in"
    And verify button Log out is displayed on the new page
    Then close the browser
