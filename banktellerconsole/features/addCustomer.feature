Feature: Add A Customer
	As a teller, 
	I need to add the customer information to the system
	So, they can start to open an account
  
  Scenario: The Customer Detail is Saved
    Given The user is in shown the add customer instructions
    When The user entered firstname "<firstName>",
		And The user entered lastname "<lastName>",
    And The user entered birth_day "<birth_day>",
    And The user entered birth_month "<birth_month>",
    And The user entered birth_year "<birth_year>",
    And The user entered PPS "<pps>",
   	And The user entered address "<address>"
    Then The customer details are saved to the database  
    And The teller is shown the message "The new customer <firstName>  <lastName> has been added".
  
  
  Examples: 
      | firstname | lastname   | birth_day | birth_month | birth_year | pps     | address         |
      | Wilmir    | Nicanor    |   18      |     3       |  1995      | 1234567 | Dublin, Ireland |
  
  
 	Scenario: The Customer Detail is Not Saved Due to Invalid Birthday Input
    Given The user is in shown the add customer instructions
    When The user entered firstname "<firstName>",
		And The user entered lastname "<lastName>",
    And The user entered birth_day "<birth_day>",
    And The user entered birth_month "<birth_month>",
    And The user entered birth_year "<birth_year>",
    And The user entered pps "<pps>",
   	And The user entered address "<address>"
    Then The customer details are saved to the database  
    And The teller is shown the message "Invalid birthday: " "<birth_day>" "<birth_month>" "<birth_year>".

  Examples: 
      | firstname | lastname   | birth_day | birth_month | birth_year | pps     | address         |
      | Wilmir    | Nicanor    |   18      |     13      |  1995      | 1234567 | Dublin, Ireland |
  
    
  Scenario: The Customer Detail is Not Saved Due to Existing PPS Registered in  the System
    Given The user is in shown the add customer instructions
    And The customer with "<pps>" is already existing in the system
    When The user entered firstname "<firstName>",
		And The user entered lastname "<lastName>",
    And The user entered birth_day "<birth_day>",
    And The user entered birth_month "<birth_month>",
    And The user entered birth_year "<birth_year>",
   	And The user entered pps "<pps>",
   	And The user entered address "<address>"
    Then The customer details are saved to the database  
    And The teller is shown the message "PPS Number Already exists: " "pps"

  Examples: 
      | firstname | lastname   | birth_day | birth_month | birth_year | pps     | address         |
      | Wilmir    | Nicanor    |   18      |     3       |  1995      | 1234567 | Dublin, Ireland |
     
