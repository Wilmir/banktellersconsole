package com.bankteller.services;


public interface ServiceAbstractFactory {
	CustomerRegistryService getCustomerRegistryService();

	AccountRegistryService getAccountRegistryService();
	
}
