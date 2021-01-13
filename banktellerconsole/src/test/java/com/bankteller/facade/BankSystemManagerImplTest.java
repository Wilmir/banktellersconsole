package com.bankteller.facade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.services.CustomerRegistryService;
import com.bankteller.services.ServiceAbstractFactory;

class BankSystemManagerImplTest {
	private static final String ADDRESS = "Dublin, Ireland";
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1995, 3, 18);
	private static final String LAST_NAME = "Nicanor";
	private static final String FIRST_NAME = "Wilmir";
	private static final String PPS_NUMBER = "1234567";
	private final CustomerRegistryService customerRegistryService = mock(CustomerRegistryService.class);
	private final ServiceAbstractFactory serviceFactory = mock(ServiceAbstractFactory.class);
	private BankSystemManager bankSystemManager;

	@BeforeEach
	void setUp() {
		when(serviceFactory.getCustomerRegistryService()).thenReturn(customerRegistryService);
		bankSystemManager = new BankSystemManagerImpl(serviceFactory);
	}
	
	@Test
	void testSuccessfulCustomerCreation() throws DataAccessException, CustomerAlreadyExistsException {
		bankSystemManager.add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
		
		verify(customerRegistryService, times(1)).add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
	}
	
	@Test
	void testUnSuccessfulCustomerCreationDueToSQLError() throws DataAccessException, CustomerAlreadyExistsException {
		
		doThrow(DataAccessException.class).when(customerRegistryService).add(anyString(), anyString(), anyObject(), anyString(), anyString());
	
		assertThrows(DataAccessException.class, () -> bankSystemManager.add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS));
		
		verify(customerRegistryService, times(1)).add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
	}
	
	@Test
	void testUnSuccessfulCustomerCreationDueToCustomerAlreadyExistError() throws DataAccessException, CustomerAlreadyExistsException {
		
		doThrow(CustomerAlreadyExistsException.class).when(customerRegistryService).add(anyString(), anyString(), anyObject(), anyString(), anyString());
	
		assertThrows(CustomerAlreadyExistsException.class, () -> bankSystemManager.add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS));
		
		verify(customerRegistryService, times(1)).add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
	}
	
	
	

}
