package com.bankteller.entities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;

public class SavingsAccount extends Account{
	public SavingsAccount() {
		super(AccountType.SAVINGS);
	}

	@Override
	Transaction withdraw(double amount) throws NotEnoughBalanceException {
		
		final double balance = this.getBalance();
		
		if(amount < balance) {
			throw new NotEnoughBalanceException("Account does not have enough balance");
		}
		
		return new Transaction(true, amount, balance - amount);
	}
}
