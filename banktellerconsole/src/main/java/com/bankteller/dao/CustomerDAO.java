package com.bankteller.dao;

import java.sql.SQLException;

import com.bankteller.entities.Customer;

public interface CustomerDAO {
	void add(final Customer customer) throws SQLException;
		
	Customer getCustomer(final String name) throws SQLException;

	void update(final Customer customer) throws SQLException;
}
