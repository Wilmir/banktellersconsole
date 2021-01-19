package com.bankteller.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

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
import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;

public class CreditServiceImplTest {
	private static final int ACCOUNT_NUMBER = 8765432;
	private static final double NEGATIVE_AMOUNT = -3345.76;
	private static final double DEPOSIT_AMOUNT = 3001.14;
	private final AccountRegistryService accountRegistryService = mock(AccountRegistryService.class);
	private final TransactionDAO transactionDAO = mock(TransactionDAO.class);
	private final DAOAbstractFactory daoFactory = mock(DAOAbstractFactory.class);
	private CreditService creditService;


	@BeforeEach
	void setUp() {
		when(daoFactory.getTransactionDAO()).thenReturn(transactionDAO);
		creditService = new CreditServiceImpl(daoFactory, accountRegistryService);
	}



	@Test
	void testSuccessfulTransaction() throws DataAccessException, AccountNotFoundException, InvalidAmountException, SQLException, WithrawalLimitExceededException, NotEnoughBalanceException {		
		final Account account = new CurrentAccount();
		account.setAccountNumber(ACCOUNT_NUMBER);

		when(accountRegistryService.getAccount(ACCOUNT_NUMBER)).thenReturn(account);		

		creditService.credit(ACCOUNT_NUMBER, DEPOSIT_AMOUNT);

		assertEquals(DEPOSIT_AMOUNT, account.getBalance());
		verify(accountRegistryService, times(1)).getAccount(ACCOUNT_NUMBER);
		verify(accountRegistryService, times(1)).update(account);
		verify(transactionDAO, times(1)).add(isA(Transaction.class), isA(Account.class));
	}


	@Test
	void testNegativeTransactionAmount() throws DataAccessException, AccountNotFoundException, InvalidAmountException, SQLException {
		final Throwable exception = assertThrows(InvalidAmountException.class, () -> creditService.credit(ACCOUNT_NUMBER, NEGATIVE_AMOUNT));

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

		final Throwable exception = assertThrows(DataAccessException.class, () -> creditService.credit(ACCOUNT_NUMBER, DEPOSIT_AMOUNT));

		assertEquals("The database failed to process the request.", exception.getMessage());
		verify(accountRegistryService, times(1)).getAccount(ACCOUNT_NUMBER);
		verify(accountRegistryService, times(1)).update(account);
		verify(transactionDAO, times(1)).add(isA(Transaction.class), isA(Account.class));
	}


	@Test
	void testUnsuccessfulTransactionDueToInexistentAccount() throws DataAccessException, AccountNotFoundException, InvalidAmountException, SQLException {						
		when(accountRegistryService.getAccount(ACCOUNT_NUMBER)).thenThrow(AccountNotFoundException.class);	

		final Throwable exception = assertThrows(AccountNotFoundException.class, () -> creditService.credit(ACCOUNT_NUMBER, DEPOSIT_AMOUNT));

		assertEquals(String.valueOf(ACCOUNT_NUMBER), exception.getMessage());
		verify(accountRegistryService, times(1)).getAccount(ACCOUNT_NUMBER);
		verify(accountRegistryService, times(0)).update(anyObject());
		verify(transactionDAO, times(0)).add(anyObject(), anyObject());
	}

}
