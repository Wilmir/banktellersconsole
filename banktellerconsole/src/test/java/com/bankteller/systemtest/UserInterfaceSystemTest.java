package com.bankteller.systemtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.bankteller.dao.DAOFactoryImpl;
import com.bankteller.dao.Database;
import com.bankteller.dao.UIIntegrationDatabaseImpl;
import com.bankteller.facade.BankSystemManager;
import com.bankteller.facade.BankSystemManagerImpl;
import com.bankteller.services.ServiceFactoryImpl;
import com.bankteller.ui.ConsoleUI;

/* This tests the integration of the UI with the BankSystemFacade
 * The test data for existing customers, accounts, and transactions are in the test database
 * The test database needs to be re-run before running this test
 */
@TestMethodOrder(OrderAnnotation.class)
class UserInterfaceSystemTest {
	private BankSystemManager bankSystemManager;
	private final static Database database = new UIIntegrationDatabaseImpl();
	private ConsoleUI bankUI;
	
	@BeforeAll
	static void connectToDB() throws ClassNotFoundException, SQLException {
		database.connect();
	}	
	
	
	@AfterAll
	static void disconnectToDB() throws ClassNotFoundException, SQLException {
		database.disconnect();
	}	
	
	
	@BeforeEach
	void setUp() {		
		bankSystemManager = new BankSystemManagerImpl(new ServiceFactoryImpl(new DAOFactoryImpl(database)));		
		bankUI = new ConsoleUI(bankSystemManager);
	}	
	
	
	@ParameterizedTest(name = "Registering {0} {1} with {2} returns {4}")
	@CsvFileSource(resources = "/uiIntegrationTest/addCustomerDetails.csv", numLinesToSkip = 1)
	@Order(1)
	void testAdditionOfCustomer(final String firstName, final String lastName, final String ppsNumber, final String address, final String expectedOutput) {
		final String input = firstName + "\n" + lastName + "\n" + ppsNumber + "\n" + address + "\n";
		final Scanner scanner = new Scanner(input);
		
		try {
			assertEquals(expectedOutput, bankUI.addCustomer(scanner));
		}finally {
			scanner.close();
		}
	}
	
	@ParameterizedTest(name = "Searching {0} {1} returns {2}")
	@CsvFileSource(resources = "/uiIntegrationTest/searchCustomer.csv", numLinesToSkip = 1)
	@Order(2)
	void testSearchForCustomer(final String firstName, final String lastName, final String expectedOutput) {
		final String input = firstName + "\n" + lastName + "\n";
		final Scanner scanner = new Scanner(input);
		
		try {
			assertEquals(expectedOutput, bankUI.searchCustomerByName(scanner));
		}finally {
			scanner.close();
		}
	}
	
	@ParameterizedTest(name = "Viewing customer {0} returns {1}")
	@CsvFileSource(resources = "/uiIntegrationTest/viewCustomer.csv", numLinesToSkip = 1)
	@Order(3)
	void testViewCustomerByID(final int customerID, final String expectedOutput) {
		final String input = customerID + "\n";
		final Scanner scanner = new Scanner(input);
		
		try {
			assertEquals(expectedOutput, bankUI.getCustomerByID(scanner));
		}finally {
			scanner.close();
		}
	}
	
	
	
	@ParameterizedTest(name = "Viewing account {0} returns {1}")
	@CsvFileSource(resources = "/uiIntegrationTest/viewAccount.csv", numLinesToSkip = 1)
	@Order(4)
	void testViewAccount(final int accountNumber, final String expectedOutput) {
		final String input = accountNumber + "\n";
		final Scanner scanner = new Scanner(input);
		
		try {
			assertEquals(expectedOutput, bankUI.viewAccount(scanner));
		}finally {
			scanner.close();
		}
	}
	

	@ParameterizedTest(name = "Adding {1} account to customer with pps number {0} returns {2}")
	@Order(5)
	@CsvFileSource(resources = "/uiIntegrationTest/addAccountDetails.csv", numLinesToSkip = 1)
	void testAdditionOfAccount(final String ppsNumber, final String accountType, final String expectedOutput) {
		final String input = ppsNumber + "\n" + accountType + "\n" ;
		final Scanner scanner = new Scanner(input);
		
		try {
			assertEquals(expectedOutput, bankUI.openAccount(scanner));
		}finally {
			scanner.close();
		}
	}
	
	@ParameterizedTest(name = "Withdrawing {1} from {0} returns {2}")
	@CsvFileSource(resources = "/uiIntegrationTest/withdrawals.csv", numLinesToSkip = 2)
	@Order(6)
	void testWithdrawals(final int accountNumber, final double amount, final String expectedOutput) {
		final String input = accountNumber + "\n" + amount + "\n" ;
		final Scanner scanner = new Scanner(input);
		
		try {
			assertEquals(expectedOutput, bankUI.withdraw(scanner));
		}finally {
			scanner.close();
		}
	}
	
	
	@ParameterizedTest(name = "Transferring {2} from {0} to {1} returns {3}")
	@CsvFileSource(resources = "/uiIntegrationTest/moneyTransfer.csv", numLinesToSkip = 1)
	@Order(7)
	void testMoneyTransfer(final int sendersAccountNumber, final int receiversAccountNumber, final double amount, final String expectedOutput) {
		final String input = sendersAccountNumber + "\n" + receiversAccountNumber + "\n" + amount + "\n" ;
		final Scanner scanner = new Scanner(input);
		
		try {
			assertEquals(expectedOutput, bankUI.transferMoney(scanner));
		}finally {
			scanner.close();
		}
	}
	

	@ParameterizedTest(name = "Depositing {1} from {0} returns {2}")
	@CsvFileSource(resources = "/uiIntegrationTest/deposit.csv", numLinesToSkip = 1)
	@Order(8)
	void testDeposits(final int accountNumber, final double amount, final String expectedOutput) {
		final String input = accountNumber + "\n" + amount + "\n" ;
		final Scanner scanner = new Scanner(input);
		
		try {
			assertEquals(expectedOutput, bankUI.deposit(scanner));
		}finally {
			scanner.close();
		}
	}
	
	


	
	
}
