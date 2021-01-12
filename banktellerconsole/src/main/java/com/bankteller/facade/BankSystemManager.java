package com.bankteller.facade;

import java.time.LocalDate;

import com.bankteller.entities.Customer;
import com.bankteller.exceptions.DataAccessException;

public interface BankSystemManager {
	void add(final String firstName, final String lastName, final LocalDate dateOfBirth, final String address) throws DataAccessException;

}
