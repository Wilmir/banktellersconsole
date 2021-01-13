package com.bankteller.dao;

import java.sql.SQLException;

import com.bankteller.entities.Account;
import com.bankteller.entities.Customer;

public interface AccountDAO {

	void add(Customer customer, Account account)  throws SQLException;

}
