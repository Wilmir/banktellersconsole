package com.bankteller.daointegrationtests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bankteller.dao.AccountDAO;
import com.bankteller.dao.AccountDAOImpl;
import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.CustomerDAOImpl;
import com.bankteller.dao.Database;
import com.bankteller.dao.MySQLDatabaseImpl;
import com.bankteller.dao.TransactionDAO;
import com.bankteller.dao.TransactionDAOImpl;
import com.bankteller.entities.Account;
import com.bankteller.entities.AccountFactory;
import com.bankteller.entities.Customer;
import com.bankteller.entities.Transaction;

class TransactionDAOIntegrationTest {
	private static final double DEPOSIT_AMOUNT = 3001.14;
	private static final String SAVINGS_ACCOUNT_INPUT = "savings";
	private static final String FIRST_NAME = "Wilmir";
	private static final String LAST_NAME = "Nicanor";
	private static final String PPS_NUMBER = "1234567";
	private static final String ADDRESS = "Dublin, Ireland";
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1995, 3, 18);
	private static final Customer NEW_CUSTOMER = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
	private static final AccountFactory accountFactory = new AccountFactory();
	private static final Account NEW_ACCOUNT = accountFactory.getAccount(SAVINGS_ACCOUNT_INPUT);
	private final static Database database = new MySQLDatabaseImpl();
	private AccountDAO accountDAO;
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	
	@BeforeAll
	static void connectToDB() throws ClassNotFoundException, SQLException {
		database.connect();
	}
	
	@BeforeEach
	void setUp() throws SQLException {
		accountDAO = new AccountDAOImpl(database);
		customerDAO = new CustomerDAOImpl(database); 
		transactionDAO = new TransactionDAOImpl(database);
	}
	
	
	@Test
	void testDepositTransaction() throws SQLException {
		customerDAO.deleteAll();
		accountDAO.deleteAll();
		transactionDAO.deleteAll();
		
		final Customer newCustomer = customerDAO.add(NEW_CUSTOMER);
		final Account newAccount = accountDAO.add(newCustomer, NEW_ACCOUNT);
		final double postTransactionBalance = newAccount.getBalance() + DEPOSIT_AMOUNT;
		
		final Transaction transaction = new Transaction(false,DEPOSIT_AMOUNT,postTransactionBalance);
		
		final Transaction newTransaction = transactionDAO.add(transaction, newAccount);
		
		assertEquals(DEPOSIT_AMOUNT, newTransaction.getAmount());
		assertFalse(newTransaction.isDebit());
		assertEquals(postTransactionBalance, newTransaction.getPostTransactionBalance());
		assertEquals(1, transactionDAO.getTransactions(newAccount).size());
	}

}

