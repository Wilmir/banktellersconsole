package com.bankteller.entities;

import java.time.LocalDate;

public class Customer {
	private int customerId;
	private String name;
	private LocalDate dateOfBirth;
	private LocalDate dateOfRegistration;
	private String address;
	
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(final String address) {
		this.address = address;
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

	@Override
	public String toString() {
		return "Customer [id=" + customerId + ", name=" + name + ", address=" + address + "]";
	}
	
}
