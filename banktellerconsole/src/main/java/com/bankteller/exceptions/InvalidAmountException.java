package com.bankteller.exceptions;

public class InvalidAmountException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -925266053552095730L;

	public InvalidAmountException(final String message) {
		super(message);
	}
}
