package com.bankteller.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Comparable<Transaction>{
	private final double amount;
	private double postTransactionBalance;
	private LocalDateTime dateCreated;
	private boolean isDebit;
	
	public Transaction(final boolean isDebit, final double amount, final double postTransactionBalance) {
		this.amount = amount;
		this.isDebit = isDebit;
		this.postTransactionBalance = postTransactionBalance;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(final LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public boolean isDebit() {
		return isDebit;
	}

	public void setDebit(final boolean isDebit) {
		this.isDebit = isDebit;
	}

	public double getPostTransactionBalance() {
		return postTransactionBalance;
	}

	public void setPostTransactionBalance(final double postTransactionBalance) {
		this.postTransactionBalance = postTransactionBalance;
	}

	@Override
	public String toString() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		final String formattedDate = formatter.format(dateCreated);
		final String formattedAmount = String.format("%.2f",amount);
		final String formattedBalance = String.format("%.2f",postTransactionBalance);
	
		return formattedDate + "\t" + ( isDebit ? (formattedAmount + "\t\t") : ("\t\t" + formattedAmount)) + "\t\t" + formattedBalance;
	}

	@Override
	public int compareTo(final Transaction otherTransaction) {
		if(dateCreated.isAfter(otherTransaction.dateCreated)) {
			return 1;
		}else {
			return -1;
		}
	}
	
	
}
