package com.bankteller.exceptions;

public class CustomerAlreadyExistsException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3759331661583920949L;

	public CustomerAlreadyExistsException(final String message) {
		super(message);
	}
}
