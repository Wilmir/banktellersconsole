Feature: Deposit Amount
  As a teller,
	I need to log cash deposits
	So, the account balance is updated and the credit transaction history can be retrieved later

  Scenario: Successful Deposit
    Given The teller is asked an account number and amount to deposit
    And The number <accountnumber> is associated with an account
    When The teller enters the account number <accountnumber>
    And The teller enters the amount <amount>
    Then The deposit transaction is saved
    And The message “Successfully deposited <amount> to <accountNumber>” is displayed

    Examples: 
      | accountNumber  | amount | 
      | 12345678       | 100.3  | 


  Scenario: Unsuccesufl Deposit for Inexistent Account
    Given The teller is asked an account number and amount to deposit
    And The number <accountnumber> is not associated with an account in the system
    When The teller enters the account number <accountnumber>
    And The teller enters the amount <amount>
    Then The deposit transaction is not saved
    And The message “No account found with this <accountNumber>”

    Examples: 
      | accountNumber  | amount | 
      | 12345678       | 100.3  | 
      
      
  Scenario: Unsuccessful Deposit of Negative or Literal Amount
    Given The teller is asked an account number and amount to deposit
    And The number <accountnumber> is associated with an account
    When The teller enters the account number <accountnumber>
    And The teller enters the amount <amount>
    Then The deposit transaction is not saved
    And The message “Invalid amount: <amount>”

    Examples: 
      | accountNumber  | amount | 
      | 12345678       | -100.3 | 
      | 12345678       | 1M     | 
      
      
    