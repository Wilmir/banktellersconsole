package com.bankteller.exceptions;

public class CustomerDoesNotExistException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5540067435469759582L;

	public CustomerDoesNotExistException(final String message) {
		super(message);
	}
}
