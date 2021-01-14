package com.bankteller.services;

import java.sql.SQLException;
import java.time.LocalDate;

import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.Customer;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.DataAccessException;


public class CustomerRegistryServiceImpl implements CustomerRegistryService{
	private final CustomerDAO customerDAO;

	public CustomerRegistryServiceImpl(final DAOAbstractFactory daoFactory) {
		this.customerDAO = daoFactory.getCustomerDAO();
	}
	
	@Override
	public Customer add(final String firstName, final String lastName, final LocalDate dateOfBirth, final String ppsNumber, final String address)
			throws DataAccessException, CustomerAlreadyExistsException {
			try {
				if(customerDAO.getCustomerByPPSNumber(ppsNumber) == null) {
					final Customer customer = new Customer(firstName, lastName, dateOfBirth, ppsNumber, address);
					
					return customerDAO.add(customer);
					
				}else {
					throw new CustomerAlreadyExistsException("Customer already exists");
				}		
			} catch (SQLException e) {
				throw new DataAccessException("The database failed to add the customer.");
			}

	}





	
	
}
