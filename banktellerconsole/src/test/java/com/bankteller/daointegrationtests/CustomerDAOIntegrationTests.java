package com.bankteller.daointegrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;

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
	private static final String FIRST_NAME2 = "Ally";
	private static final String LAST_NAME2 = "Wong";
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
		final Customer customer = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);

		final Customer retrievedCustomer =  customerDAO.add(customer);
		
		assertEquals(FIRST_NAME, retrievedCustomer.getFirstName());
		assertEquals(LAST_NAME, retrievedCustomer.getLastName());
		assertEquals(DATE_OF_BIRTH, retrievedCustomer.getDateOfBirth());
		assertEquals(PPS_NUMBER, retrievedCustomer.getPpsNumber());
		assertEquals(ADDRESS, retrievedCustomer.getAddress());
	}
	
	
	@Test
	void testGetCustomerByPPSNumber() throws SQLException{
		final Customer customer1 = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PPS_NUMBER, ADDRESS);

		customerDAO.add(customer1);
		assertEquals(1, customerDAO.getCustomers().size());		

		final Customer customer = customerDAO.getCustomerByPPSNumber(PPS_NUMBER);
		assertEquals(FIRST_NAME, customer.getFirstName());
		assertEquals(LAST_NAME, customer.getLastName());
		assertEquals(DATE_OF_BIRTH, customer.getDateOfBirth());
		assertEquals(PPS_NUMBER, customer.getPpsNumber());
		assertEquals(ADDRESS, customer.getAddress());

	}
	
	
	@AfterAll
	static void disconnectToDB() throws SQLException {
		database.disconnect();
	}
}