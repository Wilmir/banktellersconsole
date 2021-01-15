Feature: Withdraw Amount from Savings Account
  As a teller,
	I need to log cash withdrawals in savings accounts
	So, the account balance is updated and the debit transaction history can be retrieved later

  Scenario: Successful Withdrawal
    Given The teller is asked an account number and amount to withdraw
    And The number <accountnumber> is associated with an account
    And The account balance of account is <balance>
    When The teller enters the account number <accountnumber>
    And The teller enters the amount <withdrawalamount>
    And The withdrawal transaction is saved
    And The message “Successfully withdrawn <amount> from <accountNumber>” is displayed

    Examples: 
      | accountNumber  | balance  | withdrawalamount | 
      | 12345678       | 20000.50 | 19.99            |
      | 12345678       | 20000.50 | 20.00            |


  Scenario: Unsuccesufl Withdrawal Due to Not Enough Balance
		Given The teller is asked an account number and amount to withdraw
    And The number <accountnumber> is associated with an account
    And The account balance of account is <balance>
    When The teller enters the account number <accountnumber>
    And The teller enters the amount <withdrawalamount>
    Then The withdrawal transaction is NOT saved
    And The message “The account <accountNumber> does not have enough balance <balance>”.


    Examples: 
      | accountNumber  | balance  | withdrawalamount | 
      | 12345678       | 50.00    | 100              |
      
      
  Scenario: Unsuccessful Deposit of Negative or Literal Amount
    Given The teller is asked an account number and amount to deposit
    And The number <accountnumber> is associated with an account
    When The teller enters the account number <accountnumber>
    And The teller enters the amount <amount>
    Then The deposit transaction is not saved
    And The message “Invalid amount: <amount>”

    Examples: 
      | accountNumber  | amount | 
      | 88888888       | -100.3 | 
      | 12345678       | 1M     | 
      
      
    