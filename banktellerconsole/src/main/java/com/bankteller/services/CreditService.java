package com.bankteller.services;

import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;

public interface CreditService {

	void credit(final int accountNumber, final double amount) throws DataAccessException, AccountNotFoundException, InvalidAmountException;
}
