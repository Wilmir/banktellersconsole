package com.bankteller.exceptions;

public class NotEnoughBalanceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8101918333219032452L;

	public NotEnoughBalanceException(final String message) {
		super(message);
	}
}
