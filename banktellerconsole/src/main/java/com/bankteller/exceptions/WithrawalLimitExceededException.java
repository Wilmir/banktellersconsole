package com.bankteller.exceptions;

public class WithrawalLimitExceededException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4966226460884335921L;

	public WithrawalLimitExceededException(final String message) {
		super(message);
	}
}
