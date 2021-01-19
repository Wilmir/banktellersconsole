package com.bankteller.integrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
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
	private static final Customer CUSTOMER1 = new Customer(FIRST_NAME, LAST_NAME, PPS_NUMBER, ADDRESS);
	private static final String FIRST_NAME2 = "WILMIR";
	private static final String LAST_NAME2 = "NICANOR";
	private static final String PPS_NUMBER2 = "7654321";
	private static final String ADDRESS2 = "Athlone, Ireland";
	private static final Customer CUSTOMER2 = new Customer(FIRST_NAME2, LAST_NAME2, PPS_NUMBER2, ADDRESS2);

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
		customerDAO.add(CUSTOMER1);

		assertEquals(1, customerDAO.getCustomers().size());
	}


	@Test
	void testAddTwoCustomers() throws SQLException{
		customerDAO.add(CUSTOMER1);
		customerDAO.add(CUSTOMER2);

		assertEquals(2, customerDAO.getCustomers().size());		
	}


	@Test
	void testCorrectDataIsStored() throws SQLException{
		final Customer retrievedCustomer =  customerDAO.add(CUSTOMER1);

		checkIfCorrectDetailsAreRetrieved(CUSTOMER1, retrievedCustomer);
	}


	@Test
	void testGetCustomerByPPSNumber() throws SQLException{
		customerDAO.add(CUSTOMER1);
		assertEquals(1, customerDAO.getCustomers().size());		

		final Customer retrievedCustomer = customerDAO.getCustomerByPPSNumber(PPS_NUMBER);

		checkIfCorrectDetailsAreRetrieved(CUSTOMER1, retrievedCustomer);

	}

	@Test
	void testGetCustomerByNameSingleCustomer() throws SQLException{
		customerDAO.add(CUSTOMER1);

		final List<Customer> customers = customerDAO.getCustomers(FIRST_NAME, LAST_NAME);
		assertEquals(1, customers.size());

		final Customer retrievedCustomer =customers.get(0);

		checkIfCorrectDetailsAreRetrieved(CUSTOMER1, retrievedCustomer);

	}


	@Test
	void testGetCustomerByNameMultipleCustomer() throws SQLException{
		customerDAO.add(CUSTOMER1);
		customerDAO.add(CUSTOMER2);

		final List<Customer> customers = customerDAO.getCustomers(FIRST_NAME, LAST_NAME);
		assertEquals(2, customers.size());

		final Customer retrievedCustomer1 =customers.get(0);
		final Customer retrievedCustomer2 =customers.get(1);

		checkIfCorrectDetailsAreRetrieved(CUSTOMER1, retrievedCustomer1);
		checkIfCorrectDetailsAreRetrieved(CUSTOMER2, retrievedCustomer2);


	}


	private void checkIfCorrectDetailsAreRetrieved(final Customer originalCustomer, final Customer retrievedCustomer) {
		assertEquals(originalCustomer.getFirstName(), retrievedCustomer.getFirstName());
		assertEquals(originalCustomer.getLastName(), retrievedCustomer.getLastName());
		assertEquals(originalCustomer.getPpsNumber(), retrievedCustomer.getPpsNumber());
		assertEquals(originalCustomer.getAddress(), retrievedCustomer.getAddress());
	}

	@AfterAll
	static void disconnectToDB() throws SQLException {
		database.disconnect();
	}
}
