package com.bankteller.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bankteller.entities.Customer;



public class CustomerDAOImpl implements CustomerDAO{
	private final Database database;
	private static final String ADD_CUSTOMER_QUERY = "INSERT INTO customers (firstName, lastName, dateOfBirth, address) VALUES (?, ?, ?, ?)",
			GET_CUSTOMERS_QUERY = "SELECT * FROM customers",
			GET_A_SINGLE_CUSTOMER_BY_NAMEQUERY = "SELECT * FROM accounts WHERE firstName = (?) AND lastName = (?)",
			DELETE_ALL_QUERY = "DELETE FROM customers";
	
	public CustomerDAOImpl(final Database database) {
		this.database = database;
	}
	
	@Override
	public void add(final Customer customer) throws SQLException{
		final PreparedStatement preparedStatement = prepareStatement(ADD_CUSTOMER_QUERY);
		preparedStatement.setString(1, customer.getFirstName());
		preparedStatement.setString(2, customer.getLastName());
		preparedStatement.setObject(3, customer.getDateOfBirth());
		preparedStatement.setString(4, customer.getAddress());
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	

	@Override
	public List<Customer> getCustomers() throws SQLException {
		final Statement stmt = createStatement();
		final ResultSet resultSet = stmt.executeQuery(GET_CUSTOMERS_QUERY);
		final List<Customer> accountList = new ArrayList<>();
		
		while(resultSet.next()) {
			accountList.add(extractCustomer(resultSet));
		}
		resultSet.close();
		stmt.close();
		return accountList;
	}

	@Override
	public void deleteAll() throws SQLException {
		final Statement stmt = createStatement();
		stmt.executeUpdate(DELETE_ALL_QUERY);
		stmt.close();		
	}
	
	@Override
	public Customer getCustomerByName(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Customer customer) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	private Customer extractCustomer(ResultSet resultSet) throws SQLException {
		final int customerID = resultSet.getInt("id");
		final String firstName = resultSet.getString("firstName");
		final String lastName = resultSet.getString("lastName");
		final String address = resultSet.getString("address");
		final LocalDate dateOfBirth = resultSet.getObject("dateOfBirth", LocalDate.class);
		final LocalDateTime dateCreated = resultSet.getObject("dateCreated", LocalDateTime.class);
		final Customer customer = new Customer(firstName, lastName, dateOfBirth, address);
		customer.setCustomerId(customerID);
		customer.setDateOfRegistration(dateCreated);

		return customer;
	}
	
	private Statement createStatement() throws SQLException {
		return database.getConnection().createStatement();
	}
	
	
	private PreparedStatement prepareStatement(final String query) throws SQLException {
		return database.getConnection().prepareStatement(query);
	}


	
}
