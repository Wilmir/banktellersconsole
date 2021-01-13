package com.bankteller.entities;

public enum AccountType {
	CURRENT(1),
	SAVINGS(2);
	
	private final int typeId;
	
	AccountType(final int typeId){
		this.typeId = typeId;
	}
	
	public int getId() {
		return typeId;
	}
}
