Feature: As a teller,

I need to find a customer by name

So, I can quickly check if the customer is registered in the system

  Scenario: Successful Match of Existing Customers
    Given The teller is asked for the firstName and lastName of the customer
    And There are two customers with firstName <existingCustomerFirstName> and lastName <existingCustomerLastName> in the system
    When The teller enters the firstname <searchFirstName>
    And The teller enters the lastname <searchLastName>
    Then The customer <details> are displayed

    Examples: 
      | existingCustomerFirstName  | existingCustomerLastName | searchFirstName  | searchLastName | details                                             | 
      |           wilmir           |         nicanor          |       WiLmIR     |      nIcANoR   | Wilmir Nicanor  PPSNumber: 1234567 CustomerNumber:1 |
																																															    | Wilmir Nicanor  PPSNumber: 8888888 CustomerNumber:2 |

  Scenario: UnSuccessful Match of Existing Customers
    Given The teller is asked for the firstName and lastName of the customer
    And There are no customer with firstName <searchFirstName> and lastName <searchLastName> in the system
    When The teller enters the firstname <searchFirstName>
    And The teller enters the lastname <searchLastName>
    Then The <message> is displayed

    Examples: 
      | searchFirstName  | searchLastName | message              | 
      |     WiLmIR       |     nIcANoR    | No customer found    |
								
