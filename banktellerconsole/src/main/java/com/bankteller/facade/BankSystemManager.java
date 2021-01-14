package com.bankteller.facade;

import java.time.LocalDate;

import com.bankteller.entities.Account;
import com.bankteller.entities.Customer;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;
import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;


public interface BankSystemManager {
	Customer addCustomer(final String firstName, final String lastName, final LocalDate dateOfBirth, final String ppsNumber, final String address) throws DataAccessException, CustomerAlreadyExistsException;

	Account addAccount(final String ppsNumber, final String accountType) throws DataAccessException, CustomerAlreadyExistsException, CustomerDoesNotExistException;

	void credit(int accountNumber, double amount)
			throws InvalidAmountException, DataAccessException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException;

	void debit(int accountNumber, double amount)
			throws InvalidAmountException, DataAccessException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException;

}
