package com.bankteller.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bankteller.dao.AccountDAO;
import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.dao.TransactionDAO;
import com.bankteller.entities.Account;
import com.bankteller.entities.AccountFactory;
import com.bankteller.entities.AccountType;
import com.bankteller.entities.CurrentAccount;
import com.bankteller.entities.Customer;
import com.bankteller.entities.SavingsAccount;
import com.bankteller.entities.Transaction;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;


class AccountRegistryServiceImplTest {
	private static final double DEPOSIT_AMOUNT = 3001.14;
	private static final String SAVINGS_ACOUNT_TYPE = "savings";
	private static final String ADDRESS = "Dublin, Ireland";
	private static final String LAST_NAME = "Nicanor";
	private static final String FIRST_NAME = "Wilmir";
	private static final String PPS_NUMBER = "1234567";
	private static final Customer CUSTOMER1 = new Customer(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS);
	private static final int ACCOUNT_NUMBER = 8765432;
	private static final double ZERO_BALANCE = 0.00;
	private final AccountDAO accountDAO = mock(AccountDAO.class);
	private final CustomerDAO customerDAO = mock(CustomerDAO.class);
	private final TransactionDAO transactionDAO = mock(TransactionDAO.class);
	private final DAOAbstractFactory daoFactory = mock(DAOAbstractFactory.class);
	private final AccountFactory accountFactory = new AccountFactory();
	private AccountRegistryService accountRegistryService;
	
	@BeforeEach
	void setUp() {
		when(daoFactory.getAccountDAO()).thenReturn(accountDAO);
		when(daoFactory.getCustomerDAO()).thenReturn(customerDAO);
		when(daoFactory.getTransactionDAO()).thenReturn(transactionDAO);
		accountRegistryService = new AccountRegistryServiceImpl(daoFactory, accountFactory);
	}
	
	
	@Test
	void testSuccessfulCreationOfSavingsAccount() throws SQLException, DataAccessException, CustomerDoesNotExistException {
		when(customerDAO.getCustomerByPPSNumber(PPS_NUMBER)).thenReturn(CUSTOMER1);
		
		accountRegistryService.add(PPS_NUMBER, SAVINGS_ACOUNT_TYPE);
		
		verify(customerDAO, times(1)).getCustomerByPPSNumber(PPS_NUMBER);
		verify(accountDAO, times(1)).add(isA(Customer.class), isA(SavingsAccount.class));
		
	}
	
	@Test
	void testUnSuccessfulCreationDueToSQLError() throws SQLException, DataAccessException, CustomerDoesNotExistException {		
		when(customerDAO.getCustomerByPPSNumber(PPS_NUMBER)).thenThrow(SQLException.class);
		
		final Throwable exception = assertThrows(DataAccessException.class, () -> accountRegistryService.add(PPS_NUMBER, SAVINGS_ACOUNT_TYPE));
		
		assertEquals("The database failed to process the request.", exception.getMessage());
				
		verify(customerDAO, times(1)).getCustomerByPPSNumber(PPS_NUMBER);
		verify(accountDAO, times(0)).add(isA(Customer.class), isA(SavingsAccount.class));
	}
	
	@Test
	void testUnSuccessfulCreationDueToInexistentCustomer() throws SQLException, DataAccessException, CustomerDoesNotExistException {		
		
		final Throwable exception = assertThrows(CustomerDoesNotExistException.class, () -> accountRegistryService.add(PPS_NUMBER, SAVINGS_ACOUNT_TYPE));
		
		assertEquals("The customer does not exist", exception.getMessage());
				
		verify(customerDAO, times(1)).getCustomerByPPSNumber(PPS_NUMBER);
		verify(accountDAO, times(0)).add(isA(Customer.class), isA(SavingsAccount.class));
	}
	
	
	@Test
	void testSuccessfulRetrievalOfAnAccount() throws DataAccessException, SQLException, AccountNotFoundException {
		final Account account1 = new CurrentAccount();
		account1.setAccountNumber(ACCOUNT_NUMBER);

		when(accountDAO.getAccount(ACCOUNT_NUMBER)).thenReturn(account1);
		
		final Account retrievedAccount  =  accountRegistryService.getAccount(ACCOUNT_NUMBER);
		
		verify(accountDAO, times(1)).getAccount(ACCOUNT_NUMBER);
		verify(transactionDAO, times(1)).getTransactions(account1);
		assertEquals(ACCOUNT_NUMBER, retrievedAccount.getAccountNumber());
		assertEquals(AccountType.CURRENT, retrievedAccount.getType());
		assertEquals(ZERO_BALANCE, retrievedAccount.getBalance());
		assertTrue(retrievedAccount.getTransactions().isEmpty());
	}
	
	
	@Test
	void testSuccessfulRetrievalOfAnAccountWithSingleTransaction() throws DataAccessException, SQLException, AccountNotFoundException {
		final Account account1 = new CurrentAccount();
		account1.setAccountNumber(ACCOUNT_NUMBER);		
		
		final Transaction transaction = new Transaction(true, DEPOSIT_AMOUNT, account1.getBalance() + DEPOSIT_AMOUNT);
		transaction.setDateCreated(LocalDateTime.now());
		final List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		
		when(accountDAO.getAccount(ACCOUNT_NUMBER)).thenReturn(account1);
		when(transactionDAO.getTransactions(account1)).thenReturn(transactions);
		
		
		final Account retrievedAccount  =  accountRegistryService.getAccount(ACCOUNT_NUMBER);
		
		verify(accountDAO, times(1)).getAccount(ACCOUNT_NUMBER);
		verify(transactionDAO, times(1)).getTransactions(account1);
		
		assertEquals(1, retrievedAccount.getTransactions().size());
	}
	
	
	@Test
	void testSuccessfulRetrievalOfAnAccountWithMultipleTransactions() throws DataAccessException, SQLException, AccountNotFoundException {
		final Account account1 = new CurrentAccount();
		account1.setAccountNumber(ACCOUNT_NUMBER);		
		
		final Transaction transaction1 = new Transaction(true, DEPOSIT_AMOUNT, account1.getBalance() + DEPOSIT_AMOUNT);
		transaction1.setDateCreated(LocalDateTime.of(2020, 12, 20, 12, 15));
		
		final Transaction transaction2 = new Transaction(false, DEPOSIT_AMOUNT, account1.getBalance() + DEPOSIT_AMOUNT);
		transaction2.setDateCreated(LocalDateTime.of(2021, 1, 19, 3, 28));
		
		final List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction1);
		transactions.add(transaction2);

		when(accountDAO.getAccount(ACCOUNT_NUMBER)).thenReturn(account1);
		when(transactionDAO.getTransactions(account1)).thenReturn(transactions);
				
		final Account retrievedAccount  =  accountRegistryService.getAccount(ACCOUNT_NUMBER);
		
		verify(accountDAO, times(1)).getAccount(ACCOUNT_NUMBER);
		verify(transactionDAO, times(1)).getTransactions(account1);
		
		assertEquals(2, retrievedAccount.getTransactions().size());
		assertEquals(LocalDateTime.of(2021, 1, 19, 3, 28), retrievedAccount.getTransactions().get(0).getDateCreated());
		assertEquals(LocalDateTime.of(2020, 12, 20, 12, 15), retrievedAccount.getTransactions().get(1).getDateCreated());
	}
	
	
	@Test
	void testUnSuccessfulRetrievalOfAnAccountDueToSQLException() throws DataAccessException, SQLException {				
		doThrow(SQLException.class).when(accountDAO).getAccount(anyInt());

		final Throwable exception = assertThrows(DataAccessException.class, () -> accountRegistryService.getAccount(ACCOUNT_NUMBER));
		
		verify(accountDAO, times(1)).getAccount(ACCOUNT_NUMBER);
		assertEquals("The database failed to process the request.", exception.getMessage());
	}
	
	
	@Test
	void testRetrievalOfAnInexistentAccount() throws DataAccessException, SQLException, AccountNotFoundException {		
		final Throwable exception = assertThrows(AccountNotFoundException.class, () -> accountRegistryService.getAccount(ACCOUNT_NUMBER));

		verify(accountDAO, times(1)).getAccount(ACCOUNT_NUMBER);
		assertEquals("No account with account number " +  ACCOUNT_NUMBER + " found.", exception.getMessage());

	}
	
	@Test
	void testSuccessfulUpdateOfAnAccount() throws DataAccessException, SQLException, AccountNotFoundException {
		final Account account = new CurrentAccount();
		
		accountRegistryService.update(account);
		
		verify(accountDAO, times(1)).updateBalance(account);
	}
	
	
	@Test
	void testSuccessfulUpdateOfAnAccountDueToSQLException() throws DataAccessException, SQLException, AccountNotFoundException {		
		final Account account = new CurrentAccount();

		
		doThrow(SQLException.class).when(accountDAO).updateBalance(anyObject());
		
		final Throwable exception = assertThrows(DataAccessException.class, () -> accountRegistryService.update(account));
		
		assertEquals("The database failed to process the request.", exception.getMessage());
		
		verify(accountDAO, times(1)).updateBalance(account);
	}
	
	


}
