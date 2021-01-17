package com.bankteller.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;

public abstract class Account {
	private int accountNumber;
	private final AccountType type;
	private double balance;
	private final List<Transaction> transactions = new ArrayList<>();
	
	public Account(final AccountType type) {
		this.type = type;
	}
	
	
	public abstract Transaction withdraw(double amount) throws WithrawalLimitExceededException, NotEnoughBalanceException;
	
	public abstract Transaction deposit(double amount);

	public int getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(final int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public AccountType getType() {
		return type;
	}

	
	public int getTypeId() {
		return type.getId();
	}
	

	public double getBalance() {
	    BigDecimal bigDecimal = BigDecimal.valueOf(balance);
	    bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
	    return bigDecimal.doubleValue();
	}

	public void setBalance(final double balance) {
		this.balance = balance;
	}	
	
	public void addTransaction(final Transaction transaction) {
		this.transactions.add(transaction);
	}
	
	public List<Transaction> getTransactions() {
		final List<Transaction> copyOfTransactions = new ArrayList<Transaction>(transactions);
		
		Collections.sort(copyOfTransactions, Collections.reverseOrder());
		
		return copyOfTransactions;
	}

	Transaction createTransaction(final boolean isDebit, final double amount, final double newBalance) {
		final Transaction transaction = new Transaction(isDebit, amount, newBalance);
		addTransaction(transaction);
		return transaction;
	}
	
	double updateBalance(final double amount) {
		this.setBalance(balance + amount);
		return balance;
	}
	
	@Override
	public String toString() {
		return type + " ACCOUNT # : " + accountNumber + "\t\t" + "BALANCE: " +  String.format("%.2f", balance);
	}
	
}
