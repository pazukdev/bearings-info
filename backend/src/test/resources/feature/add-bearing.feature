Feature: add a new bearing

  Scenario Template: user opens bearings table and adds a new bearing
    Given app main page is opened
    When user opens bearings info
    And adds a new bearing with data: <Name>, <Type>, <Rolling element>, <Rolling elements quantity>
    Then user sees that the bearing added to table

    Scenarios:
      | Name            | Type	             | Rolling element | Rolling elements quantity |
      | 1002            | deepgroove         | ball            | 9                         |
      | NJ-203          | cylindrical roller | roller          | 999999999999              |
      | tapered_roller  | tapered roller     | tapered roller  | 0                         |