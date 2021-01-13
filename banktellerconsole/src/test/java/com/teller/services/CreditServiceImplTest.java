package com.teller.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.dao.TransactionDAO;
import com.bankteller.entities.Account;
import com.bankteller.entities.CurrentAccount;
import com.bankteller.entities.Transaction;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;
import com.bankteller.services.AccountRegistryService;
import com.bankteller.services.CreditServiceImpl;
import com.bankteller.services.TransactionService;

public class CreditServiceImplTest {
	private static final String SAVINGS_ACOUNT_TYPE = "savings";
	private static final String CREDIT_ACCOUNT_TYPE = "credit";
	private static final String ADDRESS = "Dublin, Ireland";
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1995, 3, 18);
	private static final String LAST_NAME = "Nicanor";
	private static final String FIRST_NAME = "Wilmir";
	private static final String PPS_NUMBER = "1234567";
	private static final int ACCOUNT_NUMBER = 8765432;
	private static final double ZERO_BALANCE = 0.00;
	private static final double NEGATIVE_AMOUNT = -3345.76;
	private static final double DEPOSIT_AMOUNT = 3001.14;
	private final AccountRegistryService accountRegistryService = mock(AccountRegistryService.class);
	private final TransactionDAO transactionDAO = mock(TransactionDAO.class);
	private final DAOAbstractFactory daoFactory = mock(DAOAbstractFactory.class);
	private TransactionService creditService;
	
	
	@BeforeEach
	void setUp() {
		when(daoFactory.getTransactionDAO()).thenReturn(transactionDAO);
		creditService = new CreditServiceImpl(daoFactory, accountRegistryService);
	}
	
	
	@Test
	void testNegativeTransactionAmount() throws DataAccessException, AccountNotFoundException, InvalidAmountException, SQLException {
		final Throwable exception = assertThrows(InvalidAmountException.class, () -> creditService.execute(ACCOUNT_NUMBER, NEGATIVE_AMOUNT));
		
		assertEquals("Negative withdrawal amount is not accepted " + NEGATIVE_AMOUNT, exception.getMessage());
		verify(accountRegistryService, never()).getAccount(anyInt());
		verify(accountRegistryService, never()).update(anyObject());
		verify(transactionDAO, never()).add(anyObject(), anyObject());

	}
	
	
	@Test
	void testUnsuccessfulTransactionDueToSQLError() throws DataAccessException, AccountNotFoundException, InvalidAmountException, SQLException {		
		final Account account = new CurrentAccount();
		account.setAccountNumber(ACCOUNT_NUMBER);
		
		when(accountRegistryService.getAccount(ACCOUNT_NUMBER)).thenReturn(account);		
		when(transactionDAO.add(anyObject(), anyObject())).thenThrow(SQLException.class);
		
		final Throwable exception = assertThrows(DataAccessException.class, () -> creditService.execute(ACCOUNT_NUMBER, DEPOSIT_AMOUNT));
		
		assertEquals("Transaction Failed: The database failed to store the transaction", exception.getMessage());
		verify(accountRegistryService, times(1)).getAccount(ACCOUNT_NUMBER);
		verify(accountRegistryService, times(1)).update(account);
		verify(transactionDAO, times(1)).add(isA(Transaction.class), isA(Account.class));
	}
	
	
	@Test
	void testUnsuccessfulTransactionDueToInexistentAccount() throws DataAccessException, AccountNotFoundException, InvalidAmountException, SQLException {						
		final Throwable exception = assertThrows(AccountNotFoundException.class, () -> creditService.execute(ACCOUNT_NUMBER, DEPOSIT_AMOUNT));
		
		assertEquals("No account found with this account number: " + ACCOUNT_NUMBER, exception.getMessage());
		verify(accountRegistryService, times(1)).getAccount(ACCOUNT_NUMBER);
		verify(accountRegistryService, times(0)).update(anyObject());
		verify(transactionDAO, times(0)).add(anyObject(), anyObject());
	}
	
	
	@Test
	void testSuccessfulTransaction() throws DataAccessException, AccountNotFoundException, InvalidAmountException, SQLException {		
		final Account account = new CurrentAccount();
		account.setAccountNumber(ACCOUNT_NUMBER);
		
		when(accountRegistryService.getAccount(ACCOUNT_NUMBER)).thenReturn(account);		
		
		creditService.execute(ACCOUNT_NUMBER, DEPOSIT_AMOUNT);
		
		verify(accountRegistryService, times(1)).getAccount(ACCOUNT_NUMBER);
		verify(accountRegistryService, times(1)).update(account);
		verify(transactionDAO, times(1)).add(isA(Transaction.class), isA(Account.class));
	}

}
