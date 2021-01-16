Feature: Add A Customer
	As a teller, 
	I need to add the customer information to the system
	So, they can start to open an account
  
  Scenario: The Customer Detail is Saved
    Given The user is in shown the add customer instructions
    When The user entered firstname "<firstName>",
		And The user entered lastname "<lastName>",
    And The user entered PPS "<pps>",
   	And The user entered address "<address>"
    Then The customer details are saved to the database  
    And The teller is shown the message "The new customer <firstName>  <lastName>  has been added".
  
  
  Examples: 
      | firstname | lastname   | pps     | address         |
      | Wilmir    | Nicanor    | 1234567 | Dublin, Ireland |
  
  
  Scenario: The Customer Detail is Not Saved Due to Existing PPS Registered in  the System
    Given The user is in shown the add customer instructions
    And The customer with "<pps>" is already existing in the system
    When The user entered firstname "<firstName>",
		And The user entered lastname "<lastName>",
   	And The user entered pps "<pps>",
   	And The user entered address "<address>"
    Then The customer details are saved to the database  
    And The teller is shown the message "The customer with PPS Number Already exists: <pps>"

  Examples: 
      | firstname | lastname   | pps     | address              |
      | Rhea      | Nicanor    | 9999999 | Melbourne, Australia |
     
