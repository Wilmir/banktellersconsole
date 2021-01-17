Feature: View All Customer's Accounts
	As a teller,
	I need to view all the customer’s account
	So, I can quickly view which account has an available balance for withdrawal
  
  Scenario: The Customer's Account/s Are Successfully Displayed
    Given The bank teller is asked to enter the customer ID
    And The <customerID> belongs to an existing customer
    When The bank teller enters the <customerID>
    Then The <customerDetails> is displayed 
    
  
  Examples: 
      | customerID    | customerDetails																																								 |
      | 8888888       | Wilmir Nicanor CustomerID: 8888888 \n AccountNumber: 99999985 SavingsAccount Balance: 35.00    |
   
  
 	Scenario: No Customer Detail is Displayed
    Given The bank teller is asked to enter the customer ID
    And The <customerID> DOES NOT belong to an existing customer
    When The bank teller enters the <customerID>
    Then The <message> is displayed 
  
  Examples: 
      | customerID    | message 																		 |
      | 88888888      | No customer associated with 88888888 found.  |
      | __34f         | Invalid customer ID                          |
      | __34f         | Invalid customer ID                          |
      
  

     