package com.bankteller.services;

import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.AccountFactory;


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
	public CreditService getCreditService() {
		return new CreditServiceImpl(daoFactory, getAccountRegistryService());
	}

	@Override
	public DebitService getDebitService() {
		return new DebitServiceImpl(daoFactory, getAccountRegistryService());
	}

}
