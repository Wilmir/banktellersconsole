package com.bankteller.entities;


import com.bankteller.exceptions.NotEnoughBalanceException;

public class SavingsAccount extends Account{
	public SavingsAccount() {
		super(AccountType.SAVINGS);
	}

	@Override
	public Transaction withdraw(final double amount) throws NotEnoughBalanceException {
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
