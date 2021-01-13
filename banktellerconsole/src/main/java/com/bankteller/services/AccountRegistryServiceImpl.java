package com.bankteller.services;

import java.sql.SQLException;

import com.bankteller.dao.AccountDAO;
import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.Account;
import com.bankteller.entities.AccountFactory;
import com.bankteller.entities.Customer;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;


public class AccountRegistryServiceImpl implements AccountRegistryService{
	private final AccountDAO accountDAO;
	private final CustomerDAO customerDAO;
	private final AccountFactory accountFactory;
	
	public AccountRegistryServiceImpl(final DAOAbstractFactory daoFactory, 
			final AccountFactory accountFactory) {
		this.accountDAO = daoFactory.getAccountDAO();
		this.customerDAO = daoFactory.getCustomerDAO();
		this.accountFactory = accountFactory;
	}
	
	@Override
	public void add(final String ppsNumber, final String accountType) throws DataAccessException, CustomerDoesNotExistException{
		Account account = accountFactory.getAccount(accountType);			
		try {
			Customer customer = customerDAO.getCustomerByPPSNumber(ppsNumber);

			if(customer == null) {
				throw new CustomerDoesNotExistException("The customer does not exist");
			}
			
			accountDAO.add(customer, account);
		} catch (SQLException e) {
			throw new DataAccessException("The database failed to add the account.");
		}
	}
	


}
