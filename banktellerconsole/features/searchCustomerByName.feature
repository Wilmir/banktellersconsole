Feature: Search Customers By Name
	As a teller,
	I need to find a customer by name
	So, I can quickly check if the customer is registered in the system

  Scenario: Successful Match of Existing Customers
    Given The teller is asked for the firstName and lastName of the customer
    And There are two customers with firstName <existingCustomerFirstName> and lastName <existingCustomerLastName> in the system
    When The teller enters the firstname <searchFirstName>
    And The teller enters the lastname <searchLastName>
    Then The customer <details> are displayed

    Examples: 
      | existingCustomerFirstName  | existingCustomerLastName | searchFirstName  | searchLastName | details                                                                                 | 
      |           rhea             |         nicanor          |       RheA       |      nIcANoR   | 2 Customers Found																																		    |
      																																												    | CustomerID:9999999 Name: Rhea Nicanor Address: Melbourne Australia PPSNumber: 9999999   |
																																															    | CustomerID:9999998 Name: Rhea Nicanor Address: Dublin Ireland PPSNumber: 8888888        |                |

  Scenario: UnSuccessful Match of Existing Customers
    Given The teller is asked for the firstName and lastName of the customer
    And There are no customer with firstName <searchFirstName> and lastName <searchLastName> in the system
    When The teller enters the firstname <searchFirstName>
    And The teller enters the lastname <searchLastName>
    Then The <message> is displayed

    Examples: 
      | searchFirstName  | searchLastName | message              | 
      |     Astrid       |     James      | No customer found    |
								
