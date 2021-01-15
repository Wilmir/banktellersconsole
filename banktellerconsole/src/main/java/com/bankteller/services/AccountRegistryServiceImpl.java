package com.bankteller.services;

import java.sql.SQLException;
import java.util.List;

import com.bankteller.dao.AccountDAO;
import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.dao.TransactionDAO;
import com.bankteller.entities.Account;
import com.bankteller.entities.AccountFactory;
import com.bankteller.entities.Customer;
import com.bankteller.entities.Transaction;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;


public class AccountRegistryServiceImpl implements AccountRegistryService{
	private final AccountDAO accountDAO;
	private final CustomerDAO customerDAO;
	private final TransactionDAO transactionDAO;
	private final AccountFactory accountFactory;
	
	public AccountRegistryServiceImpl(final DAOAbstractFactory daoFactory, 
			final AccountFactory accountFactory) {
		this.accountDAO = daoFactory.getAccountDAO();
		this.customerDAO = daoFactory.getCustomerDAO();
		this.transactionDAO = daoFactory.getTransactionDAO();
		this.accountFactory = accountFactory;
	}
	
	
	@Override
	public Account add(final String ppsNumber, final String accountType) throws DataAccessException, CustomerDoesNotExistException{
		final Account account = accountFactory.getAccount(accountType);			
		
		try {
			final Customer customer = customerDAO.getCustomerByPPSNumber(ppsNumber);

			if(customer == null) {
				throw new CustomerDoesNotExistException("The customer does not exist");
			}
			
			return accountDAO.add(customer, account);
			
		} catch (SQLException e) {
			throw new DataAccessException("The database failed to process the request.");
		}
	}


	@Override
	public Account getAccount(final int accountNumber) throws DataAccessException, AccountNotFoundException {
		try {
		
			final Account account = accountDAO.getAccount(accountNumber);
		
			if(account == null) {
				throw new AccountNotFoundException("No account with account number " +  accountNumber + " found.");
			}
			
			final List<Transaction> transactions = transactionDAO.getTransactions(account);
			
			
			return addRetrievedTransactionsToAccount(account, transactions);
				
			
		} catch (SQLException e) {
			throw new DataAccessException("The database failed to process the request.");
		}
	}

	
	@Override
	public void update(final Account account) throws DataAccessException {
		try {
			accountDAO.updateBalance(account);
		} catch (SQLException e) {
			throw new DataAccessException("The database failed to process the request.");
		}		
	}
	
	
	private Account addRetrievedTransactionsToAccount(final Account account, final List<Transaction> transactions) {
		for(final Transaction transaction : transactions) {
			account.addTransaction(transaction);
		}
		return account;
	}
	

}
