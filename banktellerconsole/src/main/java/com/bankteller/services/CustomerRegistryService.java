package com.bankteller.services;

import java.time.LocalDate;

import com.bankteller.entities.Customer;
import com.bankteller.exceptions.DataAccessException;

public interface CustomerRegistryService {
	void add(final String firstName, final String lastName, final LocalDate dateOfBirth, final String address) throws DataAccessException;
	
	Customer getCustomer(final String name) throws DataAccessException;

	void update(final Customer customer) throws DataAccessException;
}