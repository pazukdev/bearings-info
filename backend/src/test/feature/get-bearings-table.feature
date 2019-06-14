Feature: bearings table

  Scenario: user goes from app main page to bearings info and investigating the table of bearings
    Given: app main page is opened
    When user opens bearings info
    Then user sees a table of bearings
