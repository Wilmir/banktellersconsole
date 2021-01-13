package com.bankteller.facade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.services.AccountRegistryService;
import com.bankteller.services.CustomerRegistryService;
import com.bankteller.services.ServiceAbstractFactory;

class BankSystemManagerImplTest {
	private static final String SAVINGS_ACOUNT_TYPE = "savings";
	private static final String CREDIT_ACCOUNT_TYPE = "credit";
	private static final String ADDRESS = "Dublin, Ireland";
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1995, 3, 18);
	private static final String LAST_NAME = "Nicanor";
	private static final String FIRST_NAME = "Wilmir";
	private static final String PPS_NUMBER = "1234567";
	private final CustomerRegistryService customerRegistryService = mock(CustomerRegistryService.class);
	private final AccountRegistryService accountRegistryService = mock(AccountRegistryService.class);

	private final ServiceAbstractFactory serviceFactory = mock(ServiceAbstractFactory.class);
	private BankSystemManager bankSystemManager;

	
	@BeforeEach
	void setUp() {
		when(serviceFactory.getCustomerRegistryService()).thenReturn(customerRegistryService);
		when(serviceFactory.getAccountRegistryService()).thenReturn(accountRegistryService);

		bankSystemManager = new BankSystemManagerImpl(serviceFactory);
	}
	
	
	@Test
	void testSuccessfulCustomerCreation() throws DataAccessException, CustomerAlreadyExistsException {
		bankSystemManager.addCustomer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
		
		verify(customerRegistryService, times(1)).add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
	}
	
	
	@ParameterizedTest
	@ValueSource(strings = {CREDIT_ACCOUNT_TYPE, SAVINGS_ACOUNT_TYPE})
	void testSuccessfulAccountCreation(final String accountType) throws DataAccessException, CustomerAlreadyExistsException, CustomerDoesNotExistException {
		bankSystemManager.addAccount(PPS_NUMBER, accountType);
		
		verify(accountRegistryService, times(1)).add(PPS_NUMBER,accountType);
	}
	


	
	
	
	

}
