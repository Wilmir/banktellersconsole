package com.bankteller.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Customer {
	private int customerId;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private LocalDateTime dateOfRegistration;
	private String ppsNumber;
	private String address;
	
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

	public LocalDateTime getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(final LocalDateTime dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public String getPpsNumber() {
		return ppsNumber;
	}

	public void setPpsNumber(final String ppsNumber) {
		this.ppsNumber = ppsNumber;
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
				+ ", dateOfBirth=" + dateOfBirth + ", dateOfRegistration=" + dateOfRegistration + ", ppsNumber="
				+ ppsNumber + ", address=" + address + "]";
	}
}
