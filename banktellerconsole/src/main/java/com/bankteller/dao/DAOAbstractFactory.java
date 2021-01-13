package com.bankteller.dao;

public interface DAOAbstractFactory {
	CustomerDAO getCustomerDAO();

	AccountDAO getAccountDAO();

	TransactionDAO getTransactionDAO();

}
