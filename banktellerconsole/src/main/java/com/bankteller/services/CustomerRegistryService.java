package com.bankteller.services;

import com.bankteller.entities.Customer;

public interface CustomerRegistryService {
	void add(Customer customer);
	
	Customer getCustomer(String name);

	void update(Customer customer);
}