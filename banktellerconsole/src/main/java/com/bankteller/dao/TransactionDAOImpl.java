package com.bankteller.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bankteller.entities.Account;
import com.bankteller.entities.Transaction;

public class TransactionDAOImpl implements TransactionDAO{
	
	private final Database database;
	private static final String ADD_TRANSACTION_QUERY = "INSERT INTO transactions (accountID, isDebit, amount, postTransactionBalance) VALUES (?,?,?,?)",
			GET_ACCOUNT_TRANSACTIONS_QUERY = "SELECT * FROM transactions WHERE accountID = ",
			GET_A_TRANSACTION_QUERY = "SELECT * FROM transactions WHERE id = ",
			DELETE_ALL_QUERY = "DELETE FROM transactions";
	
	public TransactionDAOImpl(final Database database) {
		this.database = database;
	}
	
	@Override
	public Transaction add(final Transaction transaction, final Account account) throws SQLException {
		final PreparedStatement preparedStatement = prepareStatement(ADD_TRANSACTION_QUERY);
		try {
			preparedStatement.setInt(1,account.getAccountNumber());
			preparedStatement.setBoolean(2, transaction.isDebit());
			preparedStatement.setDouble(3, transaction.getAmount());
			preparedStatement.setDouble(4, transaction.getPostTransactionBalance());
			preparedStatement.executeUpdate();
		
			Transaction newTransaction = null;

			final ResultSet resultSet = preparedStatement.getGeneratedKeys();	
			
			try {
				if(resultSet.next()) {
					final int transactionID = resultSet.getInt(1);

					newTransaction = getTransaction(transactionID);
				}
			}finally {
				resultSet.close();
			}
			
			return newTransaction;
		
		
		}finally {
			preparedStatement.close();
		}

	}

	@Override
	public List<Transaction> getTransactions(final Account account) throws SQLException {
		final Statement preparedStatement = createStatement();
		final ResultSet resultSet = preparedStatement.executeQuery(GET_ACCOUNT_TRANSACTIONS_QUERY + account.getAccountNumber());
		try {
			final List<Transaction> transactions = new ArrayList<>();
			while(resultSet.next()) {
				transactions.add(extractTransaction(resultSet));
			}
			return transactions;
		} finally {
			resultSet.close();
			preparedStatement.close();
			
		}
	}

	
	private Transaction getTransaction(final int transactionId) throws SQLException {		
		final Statement statement = createStatement();
		final ResultSet resultSet = statement.executeQuery(GET_A_TRANSACTION_QUERY + transactionId);	
		try {
			Transaction transaction = null;
			
			if(resultSet.next()) {
				transaction = extractTransaction(resultSet);
			}
			
			return transaction;

		} finally {
			resultSet.close();
			statement.close();	
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
	
	private Transaction extractTransaction(final ResultSet resultSet) throws SQLException {
		final Transaction transaction = new Transaction(resultSet.getBoolean("isDebit"), resultSet.getDouble("amount"), resultSet.getDouble("postTransactionBalance"));
		transaction.setDateCreated(resultSet.getObject("dateCreated", LocalDateTime.class));
	
		return transaction;
	}

}
