package com.bankteller.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Customer {
	private int customerId;
	private final String firstName;
	private final String lastName;
	private LocalDateTime dateOfRegistration;
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

	public void setDateOfRegistration(final LocalDateTime dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
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
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfRegistration=" + dateOfRegistration + ", ppsNumber="
				+ ppsNumber + ", address=" + address + "]";
	}
}
