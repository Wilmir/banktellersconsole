package com.teller.daointegrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
	private static final String ADDRESS = "Dublin, Ireland";
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1995, 3, 18);
	
	
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
		final Customer customer = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, ADDRESS);
		
		assertEquals(0, customerDAO.getCustomers().size());

	}
	
	
	@Test
	void testAddOneCustomer() throws SQLException{
		final Customer customer = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, ADDRESS);

		customerDAO.add(customer);
		
		assertEquals(1, customerDAO.getCustomers().size());

	}
	
	
	@Test
	void testAddTwoCustomers() throws SQLException{
		final Customer customer1 = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, ADDRESS);
		final Customer customer2 = new Customer(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, ADDRESS);

		customerDAO.add(customer1);
		customerDAO.add(customer2);

		assertEquals(2, customerDAO.getCustomers().size());		
	}
	
	
	@AfterAll
	static void disconnectToDB() throws SQLException {
		database.disconnect();
	}
}
