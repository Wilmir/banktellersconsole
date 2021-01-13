package com.bankteller.dao;

import java.sql.SQLException;
import java.util.List;

import com.bankteller.entities.Account;
import com.bankteller.entities.Transaction;


public interface TransactionDAO {
	Transaction add(Transaction transaction, Account account) throws SQLException;
	
	List<Transaction> getTransactions(Account account) throws SQLException;
	
	void deleteAll() throws SQLException;
}
