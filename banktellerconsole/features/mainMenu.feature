
Feature: Main Menu for Teller
	As a teller, 
	I need a main menu in the user interface 
	So, I can create a customer, open an account, or log debit and credit transactions
  
 
  Scenario: Exit Menu Item
    Given The teller is asked to select from menu items in the main menu
    When The teller enters "exit" 
    Then The teller is shown "Thank you and Good bye!"


  Scenario: Create An Account Menu
    Given The teller is asked to select from menu items in the main menu
    When The teller enters "create" 
    Then The teller is shown the instructions to enter the customer first name, last name, pps number and address


  Scenario: Search Customer By Name Menu
    Given The teller is asked to select from menu items in the main menu
    When The teller enters "search" 
    Then The teller is shown the instructions to enter the customer first name, last name
    
    
  Scenario: View Customer By ID Menu
    Given The teller is asked to select from menu items in the main menu
    When The teller enters "viewcustomer" 
    Then The teller is shown the instructions to enter the customer ID


  Scenario: Create Account Menu
    Given The teller is asked to select from menu items in the main menu
    When The teller enters "openaccount" 
    Then The teller is shown the instructions to enter the customer PPS number, and account type to open
 
 
  Scenario: View Account Menu
    Given The teller is asked to select from menu items in the main menu
    When The teller enters "viewaccount" 
    Then The teller is shown the instructions to enter the 8 digit account number
      
      
  Scenario: Withdraw Menu
    Given The teller is asked to select from menu items in the main menu
    When The teller enters "withdraw" 
    Then The teller is shown the instructions to enter the 8 digit account number, and the amount to withdraw
    
    
  Scenario: Deposit Menu
    Given The teller is asked to select from menu items in the main menu
    When The teller enters "deposit" 
    Then The teller is shown the instructions to enter the 8 digit account number, and the amount to deposit
    
 
  Scenario: Money Transer Menu
    Given The teller is asked to select from menu items in the main menu
    When The teller enters "deposit" 
    Then The teller is shown the instructions to enter the sender and receivers 8 digit account number, and the amount to transfer