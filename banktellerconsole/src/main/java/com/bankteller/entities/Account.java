package com.bankteller.entities;

import java.time.LocalDateTime;

public abstract class Account {
	private int accountNumber;
	private AccountType type;
	private LocalDateTime dateCreated;
	private double balance;
	
	public Account(final AccountType type) {
		this.type = type;
	}
	
	public Account(final int accountNumber, final AccountType type) {
		this(type);
		this.accountNumber = accountNumber;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(final int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public AccountType getType() {
		return type;
	}

	public void setType(final AccountType type) {
		this.type = type;
	}
	
	public int getTypeId() {
		return type.getId();
	}
	
	public String getTypeName() {
		return type.name();
	}
	
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(final double balance) {
		this.balance = balance;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(final LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return type + " ACCOUNT # : " + accountNumber + "\t\t" + "BALANCE: " +  String.format("%.2f", balance);
	}

	
}
