Feature: Login Data Driven

  Scenario Outline: Positive LogIn test
    Given user Launch browser
    And opens page "https://practicetestautomation.com/practice-test-login/"
    When user enters a username "<username>" in username field
    And user enters a password "<password>" in password field
    And punch Submit button
    Then verify new page URL contains "practicetestautomation.com/logged-in-successfully/"
    And verify new page contains expected text "Congratulations" or "successfully logged in"
    And verify button Log out is displayed on the new page
    Then close the browser

    Examples: 
      | username | password    |
      | student  | Password123 |

  Scenario Outline: Negative LogIn test
    Given user Launch browser
    And opens page "https://practicetestautomation.com/practice-test-login/"
    When user enters a username "<username>" in username field
    And user enters a password "<password>" in password field
    And punch Submit button
    Then assert invalid message "<error_message>"

    Examples: 
      | username      | password          | error_message             |
      | incorrectUser | Password123       | Your username is invalid! |
      | student       | incorrectPassword | Your password is invalid! |
