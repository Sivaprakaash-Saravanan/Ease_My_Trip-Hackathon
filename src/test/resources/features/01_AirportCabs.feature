	@sanity
	Feature: Searching for Airport Cab details in EaseMyTrip Website
 
  Scenario Outline: Cab booking with Excel data for Airport Pickup and Drop
    Given the user loads test data for "<TestCase>"
    And the user clicks on Airport transfer and selects Type
    And the user enters the source city and selects SourceValue
    And the user enters the destination city and selects DestinationValue
    And the user selects the date and time
    When the user clicks the Search button
    Then the filtered cab results should be displayed
 
  Examples:
    | TestCase |
    | TC01     |
    | TC02     |