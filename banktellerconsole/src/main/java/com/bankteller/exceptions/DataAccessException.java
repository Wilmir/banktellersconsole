package com.bankteller.exceptions;

public class DataAccessException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3832915356750996968L;

	public DataAccessException(final String message) {
		super(message);
	}
}
