package com.bankteller.dao;

public class DAOFactoryImpl implements DAOAbstractFactory{
	private final Database database;
	
	public DAOFactoryImpl(final Database database) {
		this.database = database;
	}

	@Override
	public CustomerDAO getCustomerDAO() {
		// TODO Auto-generated method stub
		return new CustomerDAOImpl(database);
	}

	@Override
	public AccountDAO getAccountDAO() {
		return new AccountDAOImpl(database);
	}
		

}
