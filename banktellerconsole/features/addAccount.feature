Feature: Add An Account
	As a teller, 
	I need to open an account for the customer
	So, they can start to make bank transactions
  
  Scenario: A Current or Savings Account is Successfully Created
    Given The bank teller is shown the add account instructions
    And A customer with "<pps>" number is registered in the system
    When The bank teller entered ppsNumber "<pps>",
		And The bank teller entered the bank account type "<accountType>",
    Then The new account is created
    And The teller is shown the message "The new  <accountType> has been added to customer with pps number.".
  
  Examples: 
      | pps     | accountType| accountNumber |
      | 9999999 | current    |   10000000    |
      | 9999999 | savings    |   10000001    |
      
      

  Scenario: An Account is Not Created for Non-Registered Customers
    Given The bank teller is shown the add account instructions
    And A customer with "<pps>" number is NOT registered in the system
    When The bank teller entered ppsNumber "<pps>",
		And The bank teller entered the bank account type "<accountType>",
    Then The account is not created
    And The teller is shown the message "The customer with pps number <ppsNumber> does not exist."
  
  Examples: 
      | pps     | accountType| 
      | 9999998 | current    |
      | 9999998 | savings    |
      
      
      
  Scenario: An Account is Not Created for Incorrect Account Type
    Given The bank teller is shown the add account instructions
    And A customer with "<pps>" number is registered in the system
    When The bank teller entered ppsNumber "<pps>",
		And The bank teller entered the bank account type "<accountType>",
    Then The account is not created
    And The teller is shown the message "Invalid account type" "<accountType>". 
  
  Examples: 
      | pps     | accountType | 
      | 1234567 | curry       |
      | 1234567 | saver       |