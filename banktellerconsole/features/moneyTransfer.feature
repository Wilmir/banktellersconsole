Feature: Money Transfer
  As a teller,
	I need to log money transfer
	So, the account balances are updated and the debit and credit transaction histories can be retrieved later

  Scenario: Successful Money Transfer
    Given The teller is asked the sender and receivers account numbers and amount to withdraw
    And The number <senderaccountnumber> and <receiveraccountnumber> is associated with an account
    And The <senderaccountnumber> total withdrawal amount in  the last 24 hours is <currentdaywithdrawalamount>
    And The account balance of <senderaccountnumber> is <balance>
    When The teller enters the account number sender account number <senderaccountnumber>
    And The teller enters the account number receiver account number <receiveraccountnumber>
    And The teller enters the amount <transferamount>
    Then The daily withdrawal limit <dailywithdrawallimit> is NOT exceeded
    And The debit transaction is added in <senderaccountnumber>
    And The credit transaction is added in <receiveraccountnumber>
    And The message “Successfully withdrawn <transferamount> from <senderaccountnumber> ” is displayed
    And The message “Successfully deposited <transferamount> to <receiveraccountnumber>” is displayed


    Examples: 
      | senderaccountNumber | receiveraccountNumber | balance  | currentdaywithdrawalamount | dailywithdrawallimit | transferamount | 
      | 99999994            | 99999999              | 20000.00 | 9980                       | 10000.00             | 19.99          |
      | 99999993            | 99999999              | 20000.00 | 9980                       | 10000.00             | 20.00          |



  Scenario: Unsuccessful Transfer Due to Sender Exceeding Daily Withdrawal Limit
    Given The teller is asked the sender and receivers account numbers and amount to withdraw
    And The number <senderaccountnumber> and <receiveraccountnumber> is associated with an account
    And The <senderaccountnumber> total withdrawal amount in  the last 24 hours is <currentdaywithdrawalamount>
    And The account balance of <senderaccountnumber> is <balance>
    When The teller enters the account number sender account number <senderaccountnumber>
    And The teller enters the account number receiver account number <receiveraccountnumber>
    And The teller enters the amount <transferamount>
    Then The daily withdrawal limit <dailywithdrawallimit> is exceeded
    And The debit transaction is NOT added in <senderaccountnumber>
    And The credit transaction is NOT added in <receiveraccountnumber>
    And The message “The account <senderaccountNumber> will exceed daily withdrawal limit of <dailywithdrawallimit> euros <amount>” is displayed

    Examples: 
      | senderaccountNumber | receiveraccountNumber | balance  | currentdaywithdrawalamount | dailywithdrawallimit | transferamount | 
      | 99999992            | 99999999              | 20000.50 | 9980                       | 10000.00             | 20.01          |
      | 99999992            | 99999999              | 20000.50 | 9980                       | 10000.00             | 20.02          |
    

  Scenario: Unsuccessful Transfer Due to Sender Not Having Enough Balance
 		Given The teller is asked the sender and receivers account numbers and amount to withdraw
    And The number <senderaccountnumber> and <receiveraccountnumber> is associated with an account
    And The account balance of <senderaccountnumber> is <balance>
    When The teller enters the account number sender account number <senderaccountnumber>
    And The teller enters the account number receiver account number <receiveraccountnumber>
    And The teller enters the amount <transferamount>
    Then The debit transaction is NOT added in <senderaccountnumber>
    And The credit transaction is NOT added in <receiveraccountnumber>
    And The message “The account <senderaccountnumber> does not have enough balance <balance>” is displayed


    Examples: 
      | senderaccountNumber | receiveraccountNumber | balance  | transferamount | 
      | 99999991            | 99999999              | 50.00    | 50.01          |
      | 99999991            | 99999999              | 50.00    | 50.02          |

      
      
  Scenario: Unsuccessful Transfer of Negative or Literal Amount
    Given The teller is asked an account number and amount to deposit
    And The number <accountnumber> is associated with an account
    When The teller enters the account number <accountnumber>
    And The teller enters the amount <amount>
    Then The deposit transaction is not saved
    And The message “Invalid amount: <amount>”

    Examples: 
      | senderaccountNumber | receiveraccountNumber | balance  | transferamount | 
      | 99999994            | 99999999              | 50.00    | -50.00         |
      | 99999994            | 99999999              | 50.00    | 50M            |
      
    