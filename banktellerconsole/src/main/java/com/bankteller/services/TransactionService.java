package com.bankteller.services;

import java.sql.SQLException;

import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.dao.TransactionDAO;
import com.bankteller.entities.Account;
import com.bankteller.entities.Transaction;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;
import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;


public abstract class TransactionService {	
	private final AccountRegistryService accountRegistryService;
	private final TransactionDAO transactionDAO;

	TransactionService(final DAOAbstractFactory daoFactory,  final AccountRegistryService accountRegistryService){
   		this.accountRegistryService = accountRegistryService;
		this.transactionDAO = daoFactory.getTransactionDAO();
	}
	
	public abstract void execute(int accountNumber, double amount) throws DataAccessException, AccountNotFoundException, InvalidAmountException, WithrawalLimitExceededException, NotEnoughBalanceException;
	
	Account retrieveAccount(final int accountNumber) throws DataAccessException, AccountNotFoundException{
		try {
			return accountRegistryService.getAccount(accountNumber);
		} catch (AccountNotFoundException e) {
			throw new AccountNotFoundException(String.valueOf(accountNumber));
		}
	}
	
	void updateCurrentAccountBalance(final Account account, final double amount) throws DataAccessException {
		accountRegistryService.update(account);
	}
	
	void storeTransaction(final Transaction transaction, final Account account) throws DataAccessException{	
		try {
			transactionDAO.add(transaction, account);
		} catch (SQLException e) {
			throw new DataAccessException("Transaction Failed: The database failed to store the transaction");
		}
	}
}
