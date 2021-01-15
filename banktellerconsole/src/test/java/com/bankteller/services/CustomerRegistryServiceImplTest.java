package com.bankteller.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bankteller.dao.AccountDAO;
import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.Account;
import com.bankteller.entities.CurrentAccount;
import com.bankteller.entities.Customer;
import com.bankteller.entities.SavingsAccount;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;


class CustomerRegistryServiceImplTest {
	private static final int CUSTOMER_ID = 1234567;
	private static final String ADDRESS = "Dublin, Ireland";
	private static final String LAST_NAME = "Nicanor";
	private static final String FIRST_NAME = "Wilmir";
	private static final String PPS_NUMBER = "1234567";
	private CustomerRegistryServiceImpl customerRegistryService;
	private final CustomerDAO customerDAO = mock(CustomerDAO.class);
	private final AccountDAO accountDAO = mock(AccountDAO.class);
	private final DAOAbstractFactory daoFactory = mock(DAOAbstractFactory.class);
	
	@BeforeEach
	void setUp() {
		when(daoFactory.getCustomerDAO()).thenReturn(customerDAO);
		when(daoFactory.getAccountDAO()).thenReturn(accountDAO);

		customerRegistryService = new CustomerRegistryServiceImpl(daoFactory);
	}
	
	@Test
	void testSuccesfulCustomerCreation() throws DataAccessException, SQLException, CustomerAlreadyExistsException {
		customerRegistryService.add(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS);
	
		verify(customerDAO, times(1)).getCustomerByPPSNumber(PPS_NUMBER);
		verify(customerDAO, times(1)).add(isA(Customer.class));
	}
	
	@Test
	void testUnsuccesfulCustomerCreationDuetoSimilarPPSNumber() throws DataAccessException, SQLException, CustomerAlreadyExistsException {
		final Customer customer = new Customer(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS);
		
		when(customerDAO.getCustomerByPPSNumber(PPS_NUMBER)).thenReturn(customer);
		
		final Throwable exception = assertThrows(CustomerAlreadyExistsException.class, () -> customerRegistryService.add(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS));

		assertEquals("Customer already exists", exception.getMessage());
	
		verify(customerDAO, times(1)).getCustomerByPPSNumber(PPS_NUMBER);
		verify(customerDAO, times(0)).add(isA(Customer.class));
	}
	
	@Test
	void testUnSuccesfulCustomerCreationDueToSQLError() throws DataAccessException, SQLException {	
		doThrow(SQLException.class).when(customerDAO).add(anyObject());
		
		final Throwable exception = assertThrows(DataAccessException.class, () -> customerRegistryService.add(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS));
	
		assertEquals("The database failed to process the request.", exception.getMessage());
	}
	
	
	@Test
	void testSuccessfulRetrievalOfCustomersByName() throws SQLException, DataAccessException{
		final List<Customer> customers = new ArrayList<>();
		final Customer customer = new Customer(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS);
		customers.add(customer);
		
		when(customerDAO.getCustomers(FIRST_NAME, LAST_NAME)).thenReturn(customers);
		
		assertEquals(customers, customerRegistryService.getCustomers(FIRST_NAME, LAST_NAME));
		verify(customerDAO, times(1)).getCustomers(FIRST_NAME, LAST_NAME);
	}
	
	
	@Test
	void testUnsuccessfulRetrievalOfCustomersByNameDueToSQLError() throws SQLException, DataAccessException{		
		when(customerDAO.getCustomers(FIRST_NAME, LAST_NAME)).thenThrow(SQLException.class);
		
		final Throwable exception = assertThrows(DataAccessException.class, () -> customerRegistryService.getCustomers(FIRST_NAME, LAST_NAME));
		
		assertEquals("The database failed to process the request.", exception.getMessage());
		
		verify(customerDAO, times(1)).getCustomers(FIRST_NAME, LAST_NAME);
	}

	
	@Test
	void testSuccessfulRetrievalOfCustomerByID() throws SQLException, DataAccessException, CustomerDoesNotExistException{
		final Customer customer = new Customer(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS);
		
		when(customerDAO.getCustomerByID(CUSTOMER_ID)).thenReturn(customer);
		
		final List<Account> accounts = new ArrayList<>();
		final Account savingsAccount = new SavingsAccount();
		final Account currentAccount = new CurrentAccount();
		accounts.add(savingsAccount);
		accounts.add(currentAccount);

		when(accountDAO.getAccounts(customer)).thenReturn(accounts);
	
		final Customer retrievedCustomer = customerRegistryService.getCustomer(CUSTOMER_ID);
		
		assertEquals(customer, retrievedCustomer);
		assertEquals(2, retrievedCustomer.getAccounts().size());
		verify(customerDAO, times(1)).getCustomerByID(CUSTOMER_ID);
		verify(accountDAO, times(1)).getAccounts(customer);


	}
	
	@Test
	void testUnsuccessfulRetrievalDueToInexistentCustomer() throws SQLException, DataAccessException{
		final Throwable exception = assertThrows(CustomerDoesNotExistException.class, () -> customerRegistryService.getCustomer(CUSTOMER_ID));
		
		assertEquals("No customer associated with " + CUSTOMER_ID + " found.", exception.getMessage());
		
		verify(customerDAO, times(1)).getCustomerByID(CUSTOMER_ID);
		verify(accountDAO, never()).getAccounts(anyObject());
	}
	
	
	@Test
	void testUnsuccessfulRetrievalOfCustomerByIDDueToSQLError() throws SQLException, DataAccessException{
		when(customerDAO.getCustomerByID(CUSTOMER_ID)).thenThrow(SQLException.class);
		
		final Throwable exception = assertThrows(DataAccessException.class, () -> customerRegistryService.getCustomer(CUSTOMER_ID));
		
		assertEquals("The database failed to process the request.", exception.getMessage());
		
		verify(customerDAO, times(1)).getCustomerByID(CUSTOMER_ID);
		verify(accountDAO, never()).getAccounts(anyObject());
	}
	
	
}
