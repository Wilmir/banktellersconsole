package com.bankteller.services;

import java.time.LocalDate;

import com.bankteller.entities.Customer;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.DataAccessException;

public interface CustomerRegistryService {
	Customer add(final String firstName, final String lastName, final LocalDate dateOfBirth, final String ppsNumber, final String address) throws DataAccessException, CustomerAlreadyExistsException;
	
	Customer getCustomer(final String name) throws DataAccessException;

}