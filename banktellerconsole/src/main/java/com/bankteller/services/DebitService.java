package com.bankteller.services;

import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;
import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;

public interface DebitService {
	void debit(final int accountNumber, final double amount) throws DataAccessException, AccountNotFoundException, InvalidAmountException, WithrawalLimitExceededException, NotEnoughBalanceException;
}
