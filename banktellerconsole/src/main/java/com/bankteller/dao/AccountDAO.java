package com.bankteller.dao;

import java.sql.SQLException;
import java.util.List;

import com.bankteller.entities.Account;
import com.bankteller.entities.Customer;

public interface AccountDAO {

	Account add(Customer customer, Account account)  throws SQLException;
	
//	void add(Customer customer, Account account)  throws SQLException;

	void deleteAll() throws SQLException;

	Account getAccount(int accountNumber) throws SQLException;

	List<Account> getAccounts(Customer customer) throws SQLException;

}
