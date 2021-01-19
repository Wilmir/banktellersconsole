package com.bankteller.facade;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.bankteller.entities.Customer;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;
import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;
import com.bankteller.services.AccountRegistryService;
import com.bankteller.services.CreditService;
import com.bankteller.services.CustomerRegistryService;
import com.bankteller.services.DebitService;
import com.bankteller.services.ServiceAbstractFactory;

class BankSystemManagerImplTest {
	private static final int CUSTOMERID = 1234567;
	private static final double DEPOSIT_AMOUNT = 1000.00;
	private static final double WITHDRAWAL_AMOUNT = 1000.00;
	private static final int ACCOUNT_NUMBER = 88888888;
	private static final String SAVINGS_ACOUNT_TYPE = "savings";
	private static final String CREDIT_ACCOUNT_TYPE = "credit";
	private static final String ADDRESS = "Dublin, Ireland";
	private static final String LAST_NAME = "Nicanor";
	private static final String FIRST_NAME = "Wilmir";
	private static final String PPS_NUMBER = "1234567";
	private static final Customer CUSTOMER1 = new Customer(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS);
	private final CustomerRegistryService customerRegistryService = mock(CustomerRegistryService.class);
	private final AccountRegistryService accountRegistryService = mock(AccountRegistryService.class);
	private final CreditService creditService = mock(CreditService.class);
	private final DebitService debitService = mock(DebitService.class);
	private final ServiceAbstractFactory serviceFactory = mock(ServiceAbstractFactory.class);
	private BankSystemManager bankSystemManager;


	@BeforeEach
	void setUp() {
		when(serviceFactory.getCustomerRegistryService()).thenReturn(customerRegistryService);
		when(serviceFactory.getAccountRegistryService()).thenReturn(accountRegistryService);
		when(serviceFactory.getCreditService()).thenReturn(creditService);
		when(serviceFactory.getDebitService()).thenReturn(debitService);

		bankSystemManager = new BankSystemManagerImpl(serviceFactory);
	}


	@Test
	void testSuccessfulCustomerCreation() throws DataAccessException, CustomerAlreadyExistsException {
		bankSystemManager.addCustomer(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS);

		verify(customerRegistryService, times(1)).add(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS);
	}


	@Test
	void testGetCustomersByName() throws DataAccessException, CustomerAlreadyExistsException, CustomerDoesNotExistException, InvalidAmountException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		final List<Customer> customers = new ArrayList<>();
		customers.add(CUSTOMER1);

		when(customerRegistryService.getCustomers(FIRST_NAME, LAST_NAME)).thenReturn(customers);

		assertEquals(customers, bankSystemManager.getCustomers(FIRST_NAME, LAST_NAME));

		verify(customerRegistryService, times(1)).getCustomers(FIRST_NAME, LAST_NAME);
	}

	@Test
	void testGetCustomerByID() throws DataAccessException, CustomerAlreadyExistsException, CustomerDoesNotExistException, InvalidAmountException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		when(customerRegistryService.getCustomer(CUSTOMERID)).thenReturn(CUSTOMER1);

		assertEquals(CUSTOMER1, bankSystemManager.getCustomer(CUSTOMERID));

		verify(customerRegistryService, times(1)).getCustomer(CUSTOMERID);
	}

	@ParameterizedTest
	@ValueSource(strings = {CREDIT_ACCOUNT_TYPE, SAVINGS_ACOUNT_TYPE})
	void testSuccessfulAccountCreation(final String accountType) throws DataAccessException, CustomerAlreadyExistsException, CustomerDoesNotExistException {
		bankSystemManager.addAccount(PPS_NUMBER, accountType);

		verify(accountRegistryService, times(1)).add(PPS_NUMBER,accountType);
	}

	@Test
	void testSuccessfulDeposit() throws DataAccessException, CustomerAlreadyExistsException, CustomerDoesNotExistException, InvalidAmountException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		bankSystemManager.credit(ACCOUNT_NUMBER, DEPOSIT_AMOUNT);

		verify(creditService, times(1)).credit(ACCOUNT_NUMBER, DEPOSIT_AMOUNT);
	}

	@Test
	void testSuccessfulWithdrawal() throws DataAccessException, CustomerAlreadyExistsException, CustomerDoesNotExistException, InvalidAmountException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		bankSystemManager.debit(ACCOUNT_NUMBER, WITHDRAWAL_AMOUNT);

		verify(debitService, times(1)).debit(ACCOUNT_NUMBER, WITHDRAWAL_AMOUNT);
	}

}
