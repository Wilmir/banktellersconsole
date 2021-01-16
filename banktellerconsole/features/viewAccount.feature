Feature: Add A Customer
	As a bank teller, 
  I need to view the balance and recent transactions of an account	
  So, I can verify if debit transactions can be made  
  
  Scenario: The Account Details Are Successfully Displayed
    Given The bank teller is asked to enter the 8 digit account number
    And The account "<accountNumber>" exists in the system
    When The bank teller enters the "<accountNumber>"
    Then The accout balance is displayed 
    And The list of transactions are displayed in descending order by date.
  
  
  Examples: 
      | accountNumber |
      | 88888888      |
  
  
 	Scenario: The Account Details Are Not Displayed Due to Inexistent Account
    Given The bank teller is asked to enter the 8 digit account number
    And The account "<accountNumber>" DOES NOT exist in the system
    When The bank teller enters the "<accountNumber>"
    Then the message "No account associated with" "<accountNumber>" "exists"
  
  Examples: 
      | accountNumber |
      | 88888888      |
  
    
 	Scenario: The Account Details Are Not Displayed Due to Invalid AccountNumber
    Given The bank teller is asked to enter the 8 digit account number
    When The bank teller enters the "<accountNumber>"
    Then Then the message "Invalid account number <accountNumber> exists"

  Examples: 
      | accountNumber |
      | 1234567       |
      | 1234567a      |
      | 123456789     |
     
