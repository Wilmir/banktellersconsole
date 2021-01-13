package com.teller.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.bankteller.dao.AccountDAO;
import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.Account;
import com.bankteller.entities.AccountFactory;
import com.bankteller.entities.AccountType;
import com.bankteller.entities.CurrentAccount;
import com.bankteller.entities.Customer;
import com.bankteller.entities.SavingsAccount;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.services.AccountRegistryService;
import com.bankteller.services.AccountRegistryServiceImpl;

class AccountRegistryServiceImplTest {
	private static final String SAVINGS_ACOUNT_TYPE = "savings";
	private static final String CREDIT_ACCOUNT_TYPE = "credit";
	private static final String ADDRESS = "Dublin, Ireland";
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1995, 3, 18);
	private static final String LAST_NAME = "Nicanor";
	private static final String FIRST_NAME = "Wilmir";
	private static final String PPS_NUMBER = "1234567";
	private static final int ACCOUNT_NUMBER = 8765432;
	private static final double ZERO_BALANCE = 0.00;
	private final AccountDAO accountDAO = mock(AccountDAO.class);
	private final CustomerDAO customerDAO = mock(CustomerDAO.class);
	private final DAOAbstractFactory daoFactory = mock(DAOAbstractFactory.class);
	private final AccountFactory accountFactory = new AccountFactory();
	private AccountRegistryService accountRegistryService;
	
	@BeforeEach
	void setUp() {
		when(daoFactory.getAccountDAO()).thenReturn(accountDAO);
		when(daoFactory.getCustomerDAO()).thenReturn(customerDAO);
		accountRegistryService = new AccountRegistryServiceImpl(daoFactory, accountFactory);
	}
	
	
	@Test
	void testSuccessfulCreationOfSavingsAccount() throws SQLException, DataAccessException, CustomerDoesNotExistException {
		final Customer customer = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
		
		when(customerDAO.getCustomerByPPSNumber(PPS_NUMBER)).thenReturn(customer);
		
		accountRegistryService.add(PPS_NUMBER, SAVINGS_ACOUNT_TYPE);
		
		verify(customerDAO, times(1)).getCustomerByPPSNumber(PPS_NUMBER);
		verify(accountDAO, times(1)).add(isA(Customer.class), isA(SavingsAccount.class));
		
	}
	
	@Test
	void testUnSuccessfulCreationDueToSQLError() throws SQLException, DataAccessException, CustomerDoesNotExistException {
		final Customer customer = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
		
		when(customerDAO.getCustomerByPPSNumber(PPS_NUMBER)).thenThrow(SQLException.class);
		
		final Throwable exception = assertThrows(DataAccessException.class, () -> accountRegistryService.add(PPS_NUMBER, SAVINGS_ACOUNT_TYPE));
		
		assertEquals("The database failed to add the account.", exception.getMessage());
				
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
		assertEquals(ACCOUNT_NUMBER, retrievedAccount.getAccountNumber());
		assertEquals(AccountType.CURRENT, retrievedAccount.getType());
		assertEquals(ZERO_BALANCE, retrievedAccount.getBalance());
	}
	

	@Test
	void testUnSuccessfulRetrievalOfAnAccountDueToSQLException() throws DataAccessException, SQLException {				
		doThrow(SQLException.class).when(accountDAO).getAccount(anyInt());

		final Throwable exception = assertThrows(DataAccessException.class, () -> accountRegistryService.getAccount(ACCOUNT_NUMBER));
		
		verify(accountDAO, times(1)).getAccount(ACCOUNT_NUMBER);
		assertEquals("The database failed to fetch the account.", exception.getMessage());
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
		
		assertEquals("The database failed to update the account.", exception.getMessage());
		
		verify(accountDAO, times(1)).updateBalance(account);
	}
	
	


}
