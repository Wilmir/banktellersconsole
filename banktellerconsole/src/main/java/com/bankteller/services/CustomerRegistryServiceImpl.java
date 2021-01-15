package com.bankteller.services;

import java.sql.SQLException;
import java.util.List;

import com.bankteller.dao.AccountDAO;
import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.Account;
import com.bankteller.entities.Customer;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;


public class CustomerRegistryServiceImpl implements CustomerRegistryService{
	private final CustomerDAO customerDAO;
	private final AccountDAO accountDAO;

	public CustomerRegistryServiceImpl(final DAOAbstractFactory daoFactory) {
		this.customerDAO = daoFactory.getCustomerDAO();
		this.accountDAO = daoFactory.getAccountDAO();
	}
	
	@Override
	public Customer add(final String firstName, final String lastName, final String ppsNumber, final String address)
			throws DataAccessException, CustomerAlreadyExistsException {
			try {
				if(customerDAO.getCustomerByPPSNumber(ppsNumber) == null) {
					final Customer customer = new Customer(firstName, lastName, ppsNumber, address);
					
					return customerDAO.add(customer);
					
				}else {
					throw new CustomerAlreadyExistsException("Customer already exists");
				}		
			} catch (SQLException e) {
				throw new DataAccessException("The database failed to process the request.");
			}

	}

	@Override
	public List<Customer> getCustomers(final String firstName, final String lastName) throws DataAccessException {
		try {
			return customerDAO.getCustomers(firstName, lastName);
		} catch (SQLException e) {
			throw new DataAccessException("The database failed to process the request.");
		}
	}

	@Override
	public Customer getCustomer(final int customerID) throws DataAccessException, CustomerDoesNotExistException {
		try {
			final Customer customer =  customerDAO.getCustomerByID(customerID);
			
			if(customer == null) {
				throw new CustomerDoesNotExistException("No customer associated with " + customerID + " found.");
			}
			
			final List<Account> accounts = accountDAO.getAccounts(customer);

			return addRetrievedAccountsToCustomer(customer, accounts);
						
			
		} catch (SQLException e) {
			throw new DataAccessException("The database failed to process the request.");
		}
	}

	private Customer addRetrievedAccountsToCustomer(final Customer customer, final List<Account> accounts) throws SQLException {
		for(final Account account : accounts) {
			customer.add(account);
		}
		
		return customer;
	}
}
