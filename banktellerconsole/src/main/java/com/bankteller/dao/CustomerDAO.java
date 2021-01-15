package com.bankteller.dao;

import java.sql.SQLException;
import java.util.List;

import com.bankteller.entities.Customer;

public interface CustomerDAO {
	Customer add(final Customer customer) throws SQLException;

	List<Customer> getCustomers() throws SQLException;
	
	List<Customer> getCustomers(String firstName, String lastName) throws SQLException;
	
	Customer getCustomerByPPSNumber(String ppsNumber) throws SQLException;
	
	Customer getCustomerByID(int customerID) throws SQLException;

	void deleteAll() throws SQLException;

}
