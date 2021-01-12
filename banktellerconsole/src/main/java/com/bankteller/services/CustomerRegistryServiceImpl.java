package com.bankteller.services;

import com.bankteller.dao.CustomerDAO;
import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.Customer;


public class CustomerRegistryServiceImpl implements CustomerRegistryService{
	private final CustomerDAO customerDAO;

	CustomerRegistryServiceImpl(final DAOAbstractFactory daoFactory) {
		this.customerDAO = daoFactory.getCustomerDAO();
	}

	@Override
	public void add(final Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Customer getCustomer(final String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(final Customer customer) {
		// TODO Auto-generated method stub
		
	}

	
	
}
