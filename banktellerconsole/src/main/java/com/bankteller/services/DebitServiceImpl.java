package com.bankteller.services;

import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.Account;
import com.bankteller.entities.Transaction;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;
import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;

public class DebitServiceImpl extends TransactionService implements DebitService{	
	
	public DebitServiceImpl(final DAOAbstractFactory daoFactory, 
			final AccountRegistryService accountRegistryService) {
		
		super(daoFactory, accountRegistryService);	
	}
	
	
	@Override
	public void debit(final int accountNumber, final double amount) throws DataAccessException, AccountNotFoundException,
			InvalidAmountException, WithrawalLimitExceededException, NotEnoughBalanceException {
		execute(accountNumber, amount);
	}
	
	@Override
	public void execute(final int accountNumber, final double amount) throws DataAccessException, AccountNotFoundException, InvalidAmountException, WithrawalLimitExceededException, NotEnoughBalanceException {
		if(amount < 0) {
			throw new InvalidAmountException("Negative withdrawal amount is not accepted " + amount);
		}		
		
		final Account account = retrieveAccount(accountNumber);		
		
		final Transaction transaction = account.withdraw(amount);
		
		updateCurrentAccountBalance(account, amount);
	
		storeTransaction(transaction, account);
	}
	
}
