package com.bankteller.dao;

public class DAOFactoryImpl implements DAOAbstractFactory{
	private final Database database;
	
	public DAOFactoryImpl(final Database database) {
		this.database = database;
	}

	@Override
	public CustomerDAO getCustomerDAO() {
		return new CustomerDAOImpl(database);
	}

	@Override
	public AccountDAO getAccountDAO() {
		return new AccountDAOImpl(database);
	}

	@Override
	public TransactionDAO getTransactionDAO() {
		return new TransactionDAOImpl(database);
	}
		

}
