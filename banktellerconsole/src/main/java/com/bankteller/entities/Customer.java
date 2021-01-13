package com.bankteller.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Customer {
	private int customerId;
	private final String firstName;
	private final String lastName;
	private final LocalDate dateOfBirth;
	private LocalDateTime dateOfRegistration;
	private final String ppsNumber;
	private final String address;
	
	public Customer(final String firstName, final String lastName, final LocalDate dateOfBirth, final String ppsNumber, final String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
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

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", dateOfRegistration=" + dateOfRegistration + ", ppsNumber="
				+ ppsNumber + ", address=" + address + "]";
	}
}
