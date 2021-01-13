package com.bankteller.services;

import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.Account;
import com.bankteller.entities.Transaction;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;

public class CreditServiceImpl extends TransactionService{	
	
	public CreditServiceImpl(final DAOAbstractFactory daoFactory, 
			final AccountRegistryService accountRegistryService) {
		
		super(daoFactory, accountRegistryService);	
	}
	
	@Override
	public void execute(final int accountNumber, final double amount) throws DataAccessException, AccountNotFoundException, InvalidAmountException {
		if(amount < 0) {
			throw new InvalidAmountException("Negative withdrawal amount is not accepted " + amount);
		}		
		
		final Account account = retrieveAccount(accountNumber);		
		
		if(account == null) {
			throw new AccountNotFoundException("No account found with this account number: " + accountNumber);
		}
		
		final Transaction transaction = new Transaction(true, amount, account.getBalance() - amount);
		
		updateCurrentAccountBalance(account, amount);
	
		storeTransaction(transaction, account);
	}
	
}
