Feature: Searching for Hourly Cab details on EaseMyTrip
@sanity
Scenario: Book hourly rental with time, date and SUV filter
    Given user open the browser and navigate to EaseMyTrip
    When The user go to Cabs and select hourly
    And The user search hourly cab from Visakhapatnam on 14 September 2026 at 8:30 AM for 4 hours
    Then The user apply SUV filter
    And The user print the least fare