package com.bankteller.services;


import com.bankteller.entities.Account;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;

public interface AccountRegistryService {
	Account add(String ppsNumber, String accountType) throws DataAccessException, CustomerDoesNotExistException;

	Account getAccount(int accountNumber) throws DataAccessException, AccountNotFoundException;

	void update(Account account) throws DataAccessException;

}
