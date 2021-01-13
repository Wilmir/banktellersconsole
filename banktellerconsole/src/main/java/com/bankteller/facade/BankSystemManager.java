package com.bankteller.facade;

import java.time.LocalDate;

import com.bankteller.entities.Customer;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;

public interface BankSystemManager {
	void addCustomer(final String firstName, final String lastName, final LocalDate dateOfBirth, final String ppsNumber, final String address) throws DataAccessException, CustomerAlreadyExistsException;

	void addAccount(final String ppsNumber, final String accountType) throws DataAccessException, CustomerAlreadyExistsException, CustomerDoesNotExistException;

}
