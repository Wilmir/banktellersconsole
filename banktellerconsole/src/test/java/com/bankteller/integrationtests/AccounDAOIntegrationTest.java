package com.bankteller.integrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.bankteller.dao.AccountDAO;
import com.bankteller.dao.AccountDAOImpl;
import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.CustomerDAOImpl;
import com.bankteller.dao.Database;
import com.bankteller.dao.MySQLDatabaseImpl;
import com.bankteller.entities.Account;
import com.bankteller.entities.AccountFactory;
import com.bankteller.entities.AccountType;
import com.bankteller.entities.Customer;



class AccounDAOIntegrationTest {
	private static final double DEPOSIT_AMOUNT = 3001.14;
	private static final String SAVINGS_ACCOUNT_INPUT = "savings";
	private static final String CURRENT_ACCOUNT_INPUT = "current";
	private static final double ZERO_BALANCE = 0.00;
	private static final String FIRST_NAME = "Wilmir";
	private static final String LAST_NAME = "Nicanor";
	private static final String PPS_NUMBER = "1234567";
	private static final String ADDRESS = "Dublin, Ireland";
	private static final Customer NEW_CUSTOMER = new Customer(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS);
	private Customer retrievedCustomer;
	private AccountDAO accountDAO;
	private CustomerDAO customerDAO;
	private final AccountFactory accountFactory = new AccountFactory();
	private final static Database database = new MySQLDatabaseImpl();
	
	@BeforeAll
	static void connectToDB() throws ClassNotFoundException, SQLException {
		database.connect();
	}
	
	@BeforeEach
	void setUp() throws SQLException {
		accountDAO = new AccountDAOImpl(database);
		customerDAO = new CustomerDAOImpl(database); 
	}
	
	private static Stream<Arguments> accountTypes(){ // NOPMD by wilmirnicanor on 16/01/2021, 20:28
		return Stream.of(
				Arguments.of(CURRENT_ACCOUNT_INPUT , AccountType.CURRENT, 1),
				Arguments.of(SAVINGS_ACCOUNT_INPUT, AccountType.SAVINGS, 1)
		);
	
	}
	
	@ParameterizedTest()
	@MethodSource("accountTypes")
	void testAddOneAccount(final String input, final AccountType expectedType, final int expectedCount) throws SQLException{
		
		final Account account = accountFactory.getAccount(input);

		customerDAO.deleteAll();
		customerDAO.add(NEW_CUSTOMER);
		retrievedCustomer = customerDAO.getCustomerByPPSNumber(PPS_NUMBER);
		
		accountDAO.deleteAll();
		final Account retrievedAccount = accountDAO.add(retrievedCustomer, account);
		
		assertEquals(expectedCount, accountDAO.getAccounts(retrievedCustomer).size());
		assertEquals(expectedType, retrievedAccount.getType());
		assertEquals(ZERO_BALANCE, retrievedAccount.getBalance());

	}
	
	@Test
	void testAddTwoAccounts() throws SQLException{
		final Account account1 = accountFactory.getAccount(CURRENT_ACCOUNT_INPUT);
		final Account account2 = accountFactory.getAccount(SAVINGS_ACCOUNT_INPUT);

		customerDAO.deleteAll();
		customerDAO.add(NEW_CUSTOMER);
		retrievedCustomer = customerDAO.getCustomerByPPSNumber(PPS_NUMBER);
		
		accountDAO.deleteAll();
		final Account retrievedAccount1 = accountDAO.add(retrievedCustomer, account1);
		final Account retrievedAccount2 = accountDAO.add(retrievedCustomer, account2);

		assertEquals(2, accountDAO.getAccounts(retrievedCustomer).size());
		assertEquals(AccountType.CURRENT, retrievedAccount1.getType());
		assertEquals(AccountType.SAVINGS, retrievedAccount2.getType());
		assertEquals(ZERO_BALANCE, retrievedAccount1.getBalance());
		assertEquals(ZERO_BALANCE, retrievedAccount2.getBalance());
	}
	
	@Test
	void testUpdateAccountBalance() throws SQLException{
		final Account account = accountFactory.getAccount(CURRENT_ACCOUNT_INPUT);

		customerDAO.deleteAll();
		customerDAO.add(NEW_CUSTOMER);
		retrievedCustomer = customerDAO.getCustomerByPPSNumber(PPS_NUMBER);
		
		accountDAO.deleteAll();
		final Account retrievedAccount = accountDAO.add(retrievedCustomer, account);
		assertEquals(ZERO_BALANCE, retrievedAccount.getBalance());

		
		retrievedAccount.setBalance(DEPOSIT_AMOUNT);		
		accountDAO.updateBalance(retrievedAccount);
		
		final Account updatedAccount = accountDAO.getAccount(retrievedAccount.getAccountNumber());		
		assertEquals(DEPOSIT_AMOUNT, updatedAccount.getBalance());

	}
	

	
	@AfterAll
	static void disconnectToDB() throws SQLException {
		database.disconnect();
	}

}
