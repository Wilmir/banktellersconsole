package com.bankteller.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bankteller.entities.Customer;


public class CustomerDAOImpl implements CustomerDAO{
	private final Database database;
	private static final String ADD_CUSTOMER_QUERY = "INSERT INTO customers (firstName, lastName, ppsNumber, address) VALUES (?, ?, ?, ?)",
			GET_CUSTOMERS_QUERY = "SELECT * FROM customers",
			GET_A_SINGLE_CUSTOMER_BY_PPSNUMBER = "SELECT * FROM customers WHERE ppsNumber = ",
			GET_A_SINGLE_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE id = ",
			GET_A_SINGLE_CUSTOMER_BY_NAMEQUERY = "SELECT * FROM customers WHERE LOWER(firstName) = (?) AND LOWER(lastName) = (?)",
			DELETE_ALL_QUERY = "DELETE FROM customers";
	
	public CustomerDAOImpl(final Database database) {
		this.database = database;
	}
	
	@Override
	public Customer add(final Customer customer) throws SQLException{
		final PreparedStatement preparedStatement = prepareStatement(ADD_CUSTOMER_QUERY);
		try {
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getPpsNumber());
			preparedStatement.setString(4, customer.getAddress());
			preparedStatement.executeUpdate();
			
			
			Customer newCustomer = null;
			final ResultSet resultSet = preparedStatement.getGeneratedKeys();	
			
			try {
				if(resultSet.next()) {
					final int customerID = resultSet.getInt(1);
					newCustomer = getCustomerByID(customerID);
				}
				return newCustomer;
			}finally {
				resultSet.close();
			}

		}finally {
			preparedStatement.close();
		}
	}
	
	@Override
	public Customer getCustomerByPPSNumber(final String ppsNumber) throws SQLException {
		return getCustomerQuery(GET_A_SINGLE_CUSTOMER_BY_PPSNUMBER + ppsNumber.trim());
	}

	@Override
	public Customer getCustomerByID(final int customerID) throws SQLException {
		return getCustomerQuery(GET_A_SINGLE_CUSTOMER_BY_ID + customerID);
	}
	

	@Override
	public List<Customer> getCustomers(final String firstName, final String lastName) throws SQLException {
		final PreparedStatement preparedStatement = prepareStatement(GET_A_SINGLE_CUSTOMER_BY_NAMEQUERY);

		try {
			preparedStatement.setString(1, firstName.toLowerCase().trim());
			preparedStatement.setString(2, lastName.toLowerCase().trim());

			final ResultSet resultSet = preparedStatement.executeQuery();
			
			final List<Customer> customers = new ArrayList<>();
			
			try {
				
				while(resultSet.next()) {
					customers.add(extractCustomer(resultSet));
				}
				
				return customers;
				
			}finally {
				resultSet.close();
			}
			
		}finally {
			preparedStatement.close();
		}

	}
	
	
	@Override
	public List<Customer> getCustomers() throws SQLException {
		final Statement preparedStatement = createStatement();
		final ResultSet resultSet = preparedStatement.executeQuery(GET_CUSTOMERS_QUERY);
		final List<Customer> accountList = new ArrayList<>();
		
		try {
			while(resultSet.next()) {
				accountList.add(extractCustomer(resultSet));
			}
			return accountList;
		}finally {
			resultSet.close();
			preparedStatement.close();
		}
	}
	
	@Override
	public void deleteAll() throws SQLException {
		final Statement preparedStatement = createStatement();
		try {
			preparedStatement.executeUpdate(DELETE_ALL_QUERY);
		}finally {
			preparedStatement.close();
		}
	}
	
	private Customer getCustomerQuery(final String query) throws SQLException {
		final Statement preparedStatement = createStatement();
		final ResultSet resultSet = preparedStatement.executeQuery(query);	
		try {
			Customer customer = null;
			if(resultSet.next()) {
				customer = extractCustomer(resultSet);
			}
			return customer;
		}finally {
			resultSet.close();
			preparedStatement.close();
		}
	}
	
	
	private Customer extractCustomer(final ResultSet resultSet) throws SQLException {
		final int customerID = resultSet.getInt("id");
		final String firstName = resultSet.getString("firstName");
		final String lastName = resultSet.getString("lastName");
		final String ppsNumber = resultSet.getString("ppsNumber");
		final String address = resultSet.getString("address");
		final Customer customer = new Customer(firstName, lastName, ppsNumber, address);
		customer.setCustomerId(customerID);

		return customer;
	}
	
	
	private Statement createStatement() throws SQLException {
		return database.getConnection().createStatement();
	}
	
	
	private PreparedStatement prepareStatement(final String query) throws SQLException {
		return database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	}

	
}
