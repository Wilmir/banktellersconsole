package com.bankteller.entities;

public class AccountFactory{
	
	public Account getAccount(final String type) {
		switch(type) {
			case "savings":
				return new SavingsAccount();
			default:
				return new CurrentAccount();
		}
	}
}
