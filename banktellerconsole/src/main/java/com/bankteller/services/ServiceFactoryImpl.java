package com.bankteller.services;

import com.bankteller.dao.DAOAbstractFactory;

public class ServiceFactoryImpl implements ServiceAbstractFactory{
	private final DAOAbstractFactory daoFactory;
		
	public ServiceFactoryImpl(final DAOAbstractFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public CustomerRegistryService getCustomerRegistryService() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
