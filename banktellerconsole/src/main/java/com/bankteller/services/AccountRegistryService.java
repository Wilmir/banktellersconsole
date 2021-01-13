package com.bankteller.services;

import java.util.List;

import com.bankteller.entities.Account;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;

public interface AccountRegistryService {
	void add(String ppsNumber, String accountType) throws DataAccessException, CustomerDoesNotExistException;


}
