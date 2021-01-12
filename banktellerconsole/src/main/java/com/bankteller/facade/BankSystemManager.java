package com.bankteller.facade;

import java.time.LocalDate;

import com.bankteller.entities.Customer;

public interface BankSystemManager {
	void add(final String firstName, final String lastName, final LocalDate dateOfBirth, final String address);

}
