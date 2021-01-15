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
      | 123456789     | Wilmir Nicanor CustomerID: 123456789 \n AccountNumber: 88888888 SavingsAccount Balance: 999.99 |
   
  
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
      
  

     
