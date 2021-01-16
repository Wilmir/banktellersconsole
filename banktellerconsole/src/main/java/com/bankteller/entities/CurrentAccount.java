package com.bankteller.entities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;

public class CurrentAccount extends Account{
	private static final int MINUTES_IN_24HOURS = 1440;
	private static final double DAILY_WITHDRAWAL_LIMIT = 10000.00;
	
	public CurrentAccount() {
		super(AccountType.CURRENT);
	}

	@Override
	public Transaction withdraw(final double amount)
			throws WithrawalLimitExceededException, NotEnoughBalanceException {		

		double transactionTotalin24Hours = amount;
		
		for(final Transaction transaction : this.getTransactions()) {
			final long duration = ChronoUnit.MINUTES.between(LocalDateTime.now(), transaction.getDateCreated());
			
			if(duration < MINUTES_IN_24HOURS && transaction.isDebit()) {
				transactionTotalin24Hours += transaction.getAmount();
			}
		}

		if(transactionTotalin24Hours > DAILY_WITHDRAWAL_LIMIT) {
			throw new WithrawalLimitExceededException("Withdrawal limit is reached");
		}
		
		if(amount > this.getBalance()) {
			throw new NotEnoughBalanceException("Account does not have enough balance");
		}
	
		final double newBalance = updateBalance(-amount);

		return createTransaction(true, amount, newBalance);	
	}

	@Override
	public Transaction deposit(final double amount) {
		final double newBalance = updateBalance(amount);
		
		return createTransaction(false, amount, newBalance);
	}
	
	
}
