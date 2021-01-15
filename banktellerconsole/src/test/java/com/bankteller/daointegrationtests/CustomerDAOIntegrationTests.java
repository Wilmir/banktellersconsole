package com.bankteller.daointegrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.CustomerDAOImpl;
import com.bankteller.dao.Database;
import com.bankteller.dao.MySQLDatabaseImpl;
import com.bankteller.entities.Customer;


public class CustomerDAOIntegrationTests {
	private CustomerDAO customerDAO;
	private final static Database database = new MySQLDatabaseImpl();
	private static final String FIRST_NAME = "Wilmir";
	private static final String LAST_NAME = "Nicanor";
	private static final String PPS_NUMBER = "1234567";
	private static final String ADDRESS = "Dublin, Ireland";
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1995, 3, 18);
	private static final String FIRST_NAME2 = "WILMIR";
	private static final String LAST_NAME2 = "NICANOR";
	private static final String PPS_NUMBER2 = "7654321";
	private static final String ADDRESS2 = "Athlone, Ireland";
	private static final LocalDate DATE_OF_BIRTH2 = LocalDate.of(1989, 6, 25);
	
	@BeforeAll
	static void connectToDB() throws ClassNotFoundException, SQLException {
		database.connect();
	}
	
	
	@BeforeEach
	void setUp() throws SQLException {
		customerDAO = new CustomerDAOImpl(database);
		customerDAO.deleteAll();

	}
	

	@Test
	void testEmptyCustomer() throws SQLException{		
		assertEquals(0, customerDAO.getCustomers().size());

	}
	
	
	@Test
	void testAddOneCustomer() throws SQLException{
		final Customer customer = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);

		customerDAO.add(customer);
		
		assertEquals(1, customerDAO.getCustomers().size());
	}
	
	
	@Test
	void testAddTwoCustomers() throws SQLException{
		final Customer customer1 = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
		final Customer customer2 = new Customer(FIRST_NAME2, LAST_NAME2, DATE_OF_BIRTH2, PPS_NUMBER2, ADDRESS2);

		customerDAO.add(customer1);
		customerDAO.add(customer2);

		assertEquals(2, customerDAO.getCustomers().size());		
	}
	
	
	@Test
	void testCorrectDataIsStored() throws SQLException{
		final Customer originalCustomer = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);

		final Customer retrievedCustomer =  customerDAO.add(originalCustomer);
		
		checkIfCorrectDetailsAreRetrieved(originalCustomer, retrievedCustomer);
	}
	
	
	@Test
	void testGetCustomerByPPSNumber() throws SQLException{
		final Customer originalCustomer = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);

		customerDAO.add(originalCustomer);
		assertEquals(1, customerDAO.getCustomers().size());		

		final Customer retrievedCustomer = customerDAO.getCustomerByPPSNumber(PPS_NUMBER);
		
		checkIfCorrectDetailsAreRetrieved(originalCustomer, retrievedCustomer);

	}
	
	@Test
	void testGetCustomerByNameSingleCustomer() throws SQLException{
		final Customer originalCustomer = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);

		customerDAO.add(originalCustomer);

		final List<Customer> customers = customerDAO.getCustomers(FIRST_NAME, LAST_NAME);
		assertEquals(1, customers.size());

		final Customer retrievedCustomer =customers.get(0);
		
		checkIfCorrectDetailsAreRetrieved(originalCustomer, retrievedCustomer);

	}

	
	@Test
	void testGetCustomerByNameMultipleCustomer() throws SQLException{
		final Customer originalCustomer1 = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);
		final Customer originalCustomer2 = new Customer(FIRST_NAME2, LAST_NAME2, DATE_OF_BIRTH2, PPS_NUMBER2, ADDRESS2);

		customerDAO.add(originalCustomer1);
		customerDAO.add(originalCustomer2);

		final List<Customer> customers = customerDAO.getCustomers(FIRST_NAME, LAST_NAME);
		assertEquals(2, customers.size());

		final Customer retrievedCustomer1 =customers.get(0);
		final Customer retrievedCustomer2 =customers.get(1);

		checkIfCorrectDetailsAreRetrieved(originalCustomer1, retrievedCustomer1);
		checkIfCorrectDetailsAreRetrieved(originalCustomer2, retrievedCustomer2);


	}
	
	
	private void checkIfCorrectDetailsAreRetrieved(final Customer originalCustomer, final Customer retrievedCustomer) {
		assertEquals(originalCustomer.getFirstName(), retrievedCustomer.getFirstName());
		assertEquals(originalCustomer.getLastName(), retrievedCustomer.getLastName());
		assertEquals(originalCustomer.getDateOfBirth(), retrievedCustomer.getDateOfBirth());
		assertEquals(originalCustomer.getPpsNumber(), retrievedCustomer.getPpsNumber());
		assertEquals(originalCustomer.getAddress(), retrievedCustomer.getAddress());
	}
	
	@AfterAll
	static void disconnectToDB() throws SQLException {
		database.disconnect();
	}
}
