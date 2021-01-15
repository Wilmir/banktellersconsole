package com.bankteller.services;

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
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.DataAccessException;


class CustomerRegistryServiceImplTest {
	private static final String ADDRESS = "Dublin, Ireland";
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1995, 3, 18);
	private static final String LAST_NAME = "Nicanor";
	private static final String FIRST_NAME = "Wilmir";
	private static final String PPS_NUMBER = "1234567";
	private CustomerRegistryServiceImpl customerRegistryService;
	private final CustomerDAO customerDAO = mock(CustomerDAO.class);
	private final DAOAbstractFactory daoFactory = mock(DAOAbstractFactory.class);
	
	@BeforeEach
	void setUp() {
		when(daoFactory.getCustomerDAO()).thenReturn(customerDAO);
		customerRegistryService = new CustomerRegistryServiceImpl(daoFactory);
	}
	
	@Test
	void testSuccesfulCustomerCreation() throws DataAccessException, SQLException, CustomerAlreadyExistsException {
		customerRegistryService.add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
	
		verify(customerDAO, times(1)).getCustomerByPPSNumber(PPS_NUMBER);
		verify(customerDAO, times(1)).add(isA(Customer.class));
	}
	
	@Test
	void testUnsuccesfulCustomerCreationDuetoSimilarPPSNumber() throws DataAccessException, SQLException, CustomerAlreadyExistsException {
		final Customer customer = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
		
		when(customerDAO.getCustomerByPPSNumber(PPS_NUMBER)).thenReturn(customer);
		
		final Throwable exception = assertThrows(CustomerAlreadyExistsException.class, () -> customerRegistryService.add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS));

		assertEquals("Customer already exists", exception.getMessage());
	
		verify(customerDAO, times(1)).getCustomerByPPSNumber(PPS_NUMBER);
		verify(customerDAO, times(0)).add(isA(Customer.class));
	}
	
	@Test
	void testUnSuccesfulCustomerCreationDueToSQLError() throws DataAccessException, SQLException {	
		doThrow(SQLException.class).when(customerDAO).add(anyObject());
		
		final Throwable exception = assertThrows(DataAccessException.class, () -> customerRegistryService.add(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS));
	
		assertEquals("The database failed to add the customer.", exception.getMessage());
	}
	
	
	@Test
	void testSuccessfulRetrievalOfCustomersByName() throws SQLException, DataAccessException{
		final List<Customer> customers = new ArrayList<>();
		final Customer customer = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
		customers.add(customer);
		
		when(customerDAO.getCustomers(FIRST_NAME, LAST_NAME)).thenReturn(customers);
		
		assertEquals(customers, customerRegistryService.getCustomers(FIRST_NAME, LAST_NAME));
		verify(customerDAO, times(1)).getCustomers(FIRST_NAME, LAST_NAME);
	}
	
	
	@Test
	void testUnSuccessfulRetrievalOfCustomersByNameDueToSQLError() throws SQLException, DataAccessException{		
		when(customerDAO.getCustomers(FIRST_NAME, LAST_NAME)).thenThrow(SQLException.class);
		
		final Throwable exception = assertThrows(DataAccessException.class, () -> customerRegistryService.getCustomers(FIRST_NAME, LAST_NAME));
		
		assertEquals("The database failed to process the request.", exception.getMessage());
		
		verify(customerDAO, times(1)).getCustomers(FIRST_NAME, LAST_NAME);
	}

}
