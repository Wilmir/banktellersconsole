package com.bankteller.entities;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private int customerId;
	private final String firstName;
	private final String lastName;
	private final String ppsNumber;
	private final String address;
	private final List<Account> accounts = new ArrayList<>();
	
	public Customer(final String firstName, final String lastName, final String ppsNumber, final String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.ppsNumber = ppsNumber;
		this.address = address;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(final int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPpsNumber() {
		return ppsNumber;
	}

	public String getAddress() {
		return address;
	}

	public void add(final Account account) {
		accounts.add(account);
	}
	
	public List<Account> getAccounts(){
		return new ArrayList<Account>(accounts);
	}
	
	
	@Override
	public String toString() {
		return "CustomerId: " + customerId 
				+ "\tName: " + firstName + " " + lastName
				+ "\tAddress: " + address
				+ "\tPPSNumber: " + ppsNumber;
	}
}
