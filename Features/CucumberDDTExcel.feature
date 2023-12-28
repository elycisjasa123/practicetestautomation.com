Feature: Login Data Driven with Excel


  Scenario Outline: Login Data Driven Excel
    Given user Launch browser
    And opens page "https://practicetestautomation.com/practice-test-login/"
    And click on Login
    Then check user navigates to MyAccount Page by passing Email and Password with excel row "<row_index>"

    Examples: 
      | row_index |
      |         1 |
      |         2 |
      |         3 |
