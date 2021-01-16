package com.bankteller.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bankteller.entities.Account;
import com.bankteller.entities.CurrentAccount;
import com.bankteller.entities.Customer;
import com.bankteller.entities.SavingsAccount;


public class AccountDAOImpl implements AccountDAO{
	private final Database database;
	private static final String ADD_ACCOUNT_QUERY = "INSERT INTO accounts (type, customerID) VALUES (?, ?)",
			GET_CUSTOMER_ACCOUNTS_QUERY = "SELECT * FROM accounts WHERE customerID = ",
			GET_A_SINGLE_ACCOUNT_QUERY = "SELECT * FROM accounts WHERE id = ",
			UPDATE_ACCOUNT_BALANCE_QUERY = "UPDATE accounts SET balance = ? WHERE id = ?",
			DELETE_ALL_QUERY = "DELETE FROM accounts";

	public AccountDAOImpl(final Database database) {
		this.database = database;
	}

	
	@Override
	public Account add(final Customer customer, final Account account) throws SQLException {
		final PreparedStatement preparedStatement = prepareStatement(ADD_ACCOUNT_QUERY);
		try {
			preparedStatement.setInt(1,account.getTypeId());
			preparedStatement.setInt(2,customer.getCustomerId());
			preparedStatement.executeUpdate();
			
			Account newAccount = null;
			
			final ResultSet resultSet = preparedStatement.getGeneratedKeys();	
			
			try {
				if(resultSet.next()) {
					final int accountNumber = resultSet.getInt(1);

					newAccount = getAccount(accountNumber);
				}
			}finally {
				resultSet.close();
			}

			return newAccount;
		}finally {
			preparedStatement.close();
		}
	}
	

	@Override
	public List<Account> getAccounts(final Customer customer) throws SQLException {		
		final Statement preparedStatement = createStatement();
		final ResultSet resultSet = preparedStatement.executeQuery(GET_CUSTOMER_ACCOUNTS_QUERY + customer.getCustomerId());
		try {
			final List<Account> accountList = new ArrayList<>();
			while(resultSet.next()) {
				accountList.add(extractAccount(resultSet));
			}
			return accountList;
		} finally {
			resultSet.close();
			preparedStatement.close();
			
		}
	}

	
	@Override
	public Account getAccount(final int accountNumber) throws SQLException {		
		final Statement statement = createStatement();
		final ResultSet resultSet = statement.executeQuery(GET_A_SINGLE_ACCOUNT_QUERY + accountNumber);	
		try {
			Account account = null;
			if(resultSet.next()) {
				account = extractAccount(resultSet);
			}
			
			return account;

		} finally {
			resultSet.close();
			statement.close();	
		}
	}
	
	
	@Override
	public void updateBalance(final Account account) throws SQLException {
		final PreparedStatement preparedStatement = prepareStatement(UPDATE_ACCOUNT_BALANCE_QUERY);
		try {
			preparedStatement.setDouble(1, account.getBalance());
			preparedStatement.setInt(2, account.getAccountNumber());
			preparedStatement.executeUpdate();
		}finally {
			preparedStatement.close();		
		}
	}
	
	
	@Override
	public void deleteAll() throws SQLException {
		final Statement statement = createStatement();
		try {
			statement.executeUpdate(DELETE_ALL_QUERY);
		}finally {
			statement.close();
		}
	}
	
	
	private Statement createStatement() throws SQLException {
		return database.getConnection().createStatement();
	}
	
	
	private PreparedStatement prepareStatement(final String query) throws SQLException {
		return database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	}
	
	
	private Account extractAccount(final ResultSet resultSet) throws SQLException {
		Account account = new CurrentAccount();
		
		final int accountTypeID = resultSet.getInt("type");
		
		if(account.getTypeId() != accountTypeID) {
			account = new SavingsAccount();
		}
		
		account.setAccountNumber(resultSet.getInt("id"));
		account.setBalance(resultSet.getDouble("balance"));
	
		return account;
	}
	
}
