package com.bankteller.dao;

import java.sql.SQLException;
import java.util.List;

import com.bankteller.entities.Customer;

public interface CustomerDAO {
	void add(final Customer customer) throws SQLException;

	List<Customer> getCustomers() throws SQLException;
	
	Customer getCustomerByPPSNumber(String ppsNumber) throws SQLException;
	
	Customer getCustomerByName(String name) throws SQLException;

	void update(final Customer customer) throws SQLException;

	void deleteAll() throws SQLException;

}
