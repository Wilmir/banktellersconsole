package com.bankteller.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bankteller.entities.Account;
import com.bankteller.entities.AccountType;
import com.bankteller.entities.CurrentAccount;
import com.bankteller.entities.Customer;
import com.bankteller.entities.SavingsAccount;



public class AccountDAOImpl implements AccountDAO{
	private final Database database;
	private static final String ADD_ACCOUNT_QUERY = "INSERT INTO accounts (type, customerID) VALUES (?, ?)",
			GET_CUSTOMER_ACCOUNTS_QUERY = "SELECT * FROM accounts WHERE customerID = ",
			GET_A_SINGLE_ACCOUNT_QUERY = "SELECT * FROM accounts WHERE id = ",
			GET_ACCOUNT_TRANSACTIONS_QUERY = "SELECT * FROM transactions WHERE accountID = ",
			UPDATE_ACCOUNT_BALANCE_QUERY = "UPDATE accounts SET balance = ? WHERE id = ?",
			DELETE_ALL_QUERY = "DELETE FROM accounts";

	public AccountDAOImpl(final Database database) {
		this.database = database;
	}

	
	@Override
	public Account add(final Customer customer, Account account) throws SQLException {
		final PreparedStatement preparedStatement = prepareStatement(ADD_ACCOUNT_QUERY);
		preparedStatement.setInt(1,account.getTypeId());
		preparedStatement.setInt(2,customer.getCustomerId());
		preparedStatement.executeUpdate();
		
		
		Account newAccount = null;
		
		final ResultSet resultSet = preparedStatement.getGeneratedKeys();	
		
		if(resultSet.next()) {
			int accountNumber = resultSet.getInt(1);

			newAccount = getAccount(accountNumber);
		
		}
		
		resultSet.close();
		preparedStatement.close();		
		
		return newAccount;
	}
	

	@Override
	public List<Account> getAccounts(final Customer customer) throws SQLException {		
		final Statement preparedStatement = createStatement();
		final ResultSet resultSet = preparedStatement.executeQuery(GET_CUSTOMER_ACCOUNTS_QUERY + customer.getCustomerId());
		final List<Account> accountList = new ArrayList<>();
		while(resultSet.next()) {
			accountList.add(extractAccount(resultSet));
		}
		resultSet.close();
		preparedStatement.close();
		return accountList;
	}

	
	@Override
	public Account getAccount(final int accountNumber) throws SQLException {		
		final Statement statement = createStatement();
		final ResultSet resultSet = statement.executeQuery(GET_A_SINGLE_ACCOUNT_QUERY + accountNumber);	
		Account account = null;
		if(resultSet.next()) {
			account = extractAccount(resultSet);
		}
		resultSet.close();
		statement.close();
		return account;
	}
	
	
	@Override
	public void updateBalance(Account account) throws SQLException {
		final PreparedStatement preparedStatement = prepareStatement(UPDATE_ACCOUNT_BALANCE_QUERY);
		preparedStatement.setDouble(1, account.getBalance());
		preparedStatement.setInt(2, account.getAccountNumber());
		preparedStatement.executeUpdate();
		preparedStatement.close();		
	}
	
	
	@Override
	public void deleteAll() throws SQLException {
		final Statement statement = createStatement();
		statement.executeUpdate(DELETE_ALL_QUERY);
		statement.close();
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
		account.setDateCreated(resultSet.getObject("dateCreated", LocalDateTime.class));
	
		return account;
	}
	
}
