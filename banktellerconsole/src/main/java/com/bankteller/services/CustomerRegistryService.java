package com.bankteller.services;

import java.util.List;

import com.bankteller.entities.Customer;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;

public interface CustomerRegistryService {
	Customer add(final String firstName, final String lastName, final String ppsNumber, final String address) throws DataAccessException, CustomerAlreadyExistsException;
	
	List<Customer> getCustomers(final String firstName, final String lastName) throws DataAccessException;
	
	Customer getCustomer(final int customerID) throws DataAccessException, CustomerDoesNotExistException;
}