package com.bankteller.exceptions;

public class AccountNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4261439270178188964L;

	public AccountNotFoundException(String message) {
		super(message);
	}
	
}
