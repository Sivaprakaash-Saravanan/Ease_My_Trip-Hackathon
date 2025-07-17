Feature: Searching for Hotel details on EaseMyTrip
  @regression
  Scenario: Fetching the hotels available for filterd options
    Given the user clicks the hotels tab
    And the user enters city name as Delhi and selects Dwarka with city as Delhi
    And the user selects the check-in date as Aug and 15
    And the user selects the check-out date as Aug and 20 with number of rooms
    When the user clicks the  Search button
    Then the filtered hotel names and prices should be displayed