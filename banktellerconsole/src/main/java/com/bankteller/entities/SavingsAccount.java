package com.bankteller.entities;


import com.bankteller.exceptions.NotEnoughBalanceException;

public class SavingsAccount extends Account{
	public SavingsAccount() {
		super(AccountType.SAVINGS);
	}

	@Override
	Transaction withdraw(final double amount) throws NotEnoughBalanceException {
		
		final double balance = this.getBalance();
		
		if(amount < balance) {
			throw new NotEnoughBalanceException("Account does not have enough balance");
		}
		
		return new Transaction(true, amount, balance - amount);
	}
}
