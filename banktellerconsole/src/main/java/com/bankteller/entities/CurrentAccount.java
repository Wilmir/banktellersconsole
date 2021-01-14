package com.bankteller.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	public Transaction withdraw(double amount)
			throws WithrawalLimitExceededException, NotEnoughBalanceException {
		final LocalDateTime currentTime = LocalDateTime.now();
		

		double transactionTotalin24Hours = amount;
		
		for(final Transaction transaction : this.getTransactions()) {
			final long duration = ChronoUnit.MINUTES.between(LocalDateTime.now(), transaction.getDateCreated());
			
			if(duration < MINUTES_IN_24HOURS) {
				transactionTotalin24Hours += transaction.getAmount();
			}
		}

		if(transactionTotalin24Hours > DAILY_WITHDRAWAL_LIMIT) {
			throw new WithrawalLimitExceededException("Withdrawal limit is reached");
		}
		
	    BigDecimal bd = BigDecimal.valueOf(this.getBalance());
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    final double balance = bd.doubleValue();
	
	    
		if(amount > balance) {
			throw new NotEnoughBalanceException("Account does not have enough balance");
		}
	
		this.setBalance(balance - amount);
		final Transaction transaction = new Transaction(true, amount, balance - amount);
		this.addTransaction(transaction);
		
		return transaction;
	}
	
	
}
