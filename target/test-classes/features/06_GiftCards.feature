
@fieldLevel
Feature: Searching for Giftards details on EaseMyTrip
  
  Scenario Outline: validating the giftcards by giving Email and Mobile Number
    Given the user clicks on More tab and selects GiftCard to test "<TestCases>"
    And scroll to festival and clicks festival then selects Diwali GiftCard
    And filling the necessary data in the form which is in excel file
    When the user enters Email and MobileNumber then clicks Accept check box
    Then it displays error message and captures screenshot


      Examples:
      |TestCases|
      | TC1 |
      | TC2 |
      | TC3 |