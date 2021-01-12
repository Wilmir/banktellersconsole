package com.bankteller.entities;

import java.time.LocalDate;

public class Customer {
	private int customerId;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private LocalDate dateOfRegistration;
	private String address;
	
	public Customer(final String firstName, final String lastName, final LocalDate dateOfBirth, final String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
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

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(final LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(final LocalDate dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", dateOfRegistration=" + dateOfRegistration + ", address=" + address
				+ "]";
	}
}
