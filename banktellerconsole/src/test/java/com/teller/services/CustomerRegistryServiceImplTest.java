package com.teller.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.Customer;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.services.CustomerRegistryServiceImpl;


class CustomerRegistryServiceImplTest {
	private static final String ADDRESS = "Dublin, Ireland";
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1995, 3, 18);
	private static final String LAST_NAME = "Nicanor";
	private static final String FIRST_NAME = "Wilmir";
	private CustomerRegistryServiceImpl customerRegistryService;
	private final CustomerDAO customerDAO = mock(CustomerDAO.class);
	private final DAOAbstractFactory daoFactory = mock(DAOAbstractFactory.class);
	
	@BeforeEach
	void setUp() {
		when(daoFactory.getCustomerDAO()).thenReturn(customerDAO);
		customerRegistryService = new CustomerRegistryServiceImpl(daoFactory);
	}
	
	@Test
	void testSuccesfulCustomerCreation() throws DataAccessException, SQLException {
		customerRegistryService.add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, ADDRESS);
	
		verify(customerDAO, times(1)).add(isA(Customer.class));
	}
	
	@Test
	void testUnSuccesfulCustomerCreation() throws DataAccessException, SQLException {	
		doThrow(SQLException.class).when(customerDAO).add(anyObject());
		
		Throwable exception = assertThrows(DataAccessException.class, () -> customerRegistryService.add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, ADDRESS));
	
		assertEquals("The database failed to add the customer.", exception.getMessage());
	}

}
