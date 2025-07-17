Feature: Searching for Outstation Cab details on EaseMyTrip
@sanity
Scenario: Book Outstation with one-way trip
   Given The user go to Cabs and select Outstation OneWay Trip
   When The user select from city and to city
   And The user selects Date
   And The user choose Time
   Then The user click Search
   And The user apply SUV filter and printing results based on more options
   
   
   @sanity
   Scenario: Book Outstation for round-way trip
   Given The user go to Cabs and select Outstation Round Trip
   When user select From city and to city
   And The user selects pickUp date
   And The user choose pick up time
   And The user selects return date
   And The user choose return time
   Then user click Search
   And The user apply Filter to fetch data
 