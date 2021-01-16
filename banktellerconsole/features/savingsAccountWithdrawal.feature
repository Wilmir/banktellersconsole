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
      | 99999990       | 20000.50 | 20000.50         |
      | 99999989       | 20000.50 | 20000.49         |


  Scenario: Unsuccessful Withdrawal Due to Not Enough Balance
		Given The teller is asked an account number and amount to withdraw
    And The number <accountnumber> is associated with an account
    And The account balance of account is <balance>
    When The teller enters the account number <accountnumber>
    And The teller enters the amount <withdrawalamount>
    Then The withdrawal transaction is NOT saved
    And The message “The account <accountNumber> does not have enough balance <balance>”.


    Examples: 
      | accountNumber  | balance  | withdrawalamount | 
      | 99999990       | 50.00    | 100              |
      
      
  Scenario: Unsuccessful Withdrawal of Negative or Literal Amount
    Given The teller is asked an account number and amount to deposit
    And The number <accountnumber> is associated with an account
    When The teller enters the account number <accountnumber>
    And The teller enters the amount <amount>
    Then The deposit transaction is not saved
    And The message “Invalid amount: <amount>”

    Examples: 
      | accountNumber  | amount | 
      | 99999990       | -100.3 | 
      | 99999990       | 1M     | 
      
      
    