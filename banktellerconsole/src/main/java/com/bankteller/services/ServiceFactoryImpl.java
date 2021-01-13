package com.bankteller.services;

import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.AccountFactory;
import com.bankteller.entities.TransactionType;


public class ServiceFactoryImpl implements ServiceAbstractFactory{
	private final DAOAbstractFactory daoFactory;
		
	public ServiceFactoryImpl(final DAOAbstractFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public CustomerRegistryService getCustomerRegistryService() {
		return new CustomerRegistryServiceImpl(daoFactory);
	}

	@Override
	public AccountRegistryService getAccountRegistryService() {
		// TODO Auto-generated method stub
		return new AccountRegistryServiceImpl(daoFactory, new AccountFactory());
	}

	@Override
	public TransactionService getTransactionService(final TransactionType transactionType) {
		switch(transactionType) {
			default:
				return new CreditServiceImpl(daoFactory, getAccountRegistryService());
	}	}
	
}
