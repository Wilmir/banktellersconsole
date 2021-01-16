package com.bankteller.facade;

import java.util.List;

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
	Customer addCustomer(final String firstName, final String lastName, final String ppsNumber, final String address) throws DataAccessException, CustomerAlreadyExistsException;

	List<Customer> getCustomers(final String firstName, final String lastName) throws DataAccessException;
	
	Customer getCustomer(final int customerID) throws DataAccessException, CustomerDoesNotExistException;
	
	Account addAccount(final String ppsNumber, final String accountType) throws DataAccessException, CustomerDoesNotExistException;

	Account getAccount(int accountNumber) throws DataAccessException, AccountNotFoundException;
	
	
	void credit(int accountNumber, double amount)
			throws InvalidAmountException, DataAccessException, AccountNotFoundException;

	void debit(int accountNumber, double amount)
			throws InvalidAmountException, DataAccessException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException;

}
