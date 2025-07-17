#Feature: Searching for Airport Cab details in EaseMyTrip Website
#			@sanity
#			Scenario: Fetching the cab details for Airport pickup
#				Given the user clicks on Airport transfer and selects Pickup
#				And the user enters the source city as "coimbatore" and selects " Coimbatore International Airport"
#				And the user enters the destination city as "munnar" and selects " munnar"
#				And the user selects the date as "15/July/2025" and time as "4:30 PM"
#				When the user clicks the Search button
#				Then the filtered cab names and prices should be displayed
#				@sanity
#			Scenario: Fetching the cab details for Airport drop
#				Given the user clicks on Airport transfer and selects drop
#				And user enters the source city as "munnar" and selects " munnar"
#				And user enters the destination city as "coimbatore" and selects " Coimbatore International Airport"
#				And user selects the date as "15/July/2025" and time as "4:30 PM"
#				When the user clicks the Search button and selects Sedan
#				Then the filtered cab price should be displayed
				
				
	Feature: Searching for Airport Cab details in EaseMyTrip Website
 
  @sanity
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