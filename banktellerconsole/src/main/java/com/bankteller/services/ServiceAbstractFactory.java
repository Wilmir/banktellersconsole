package com.bankteller.services;

import com.bankteller.entities.TransactionType;

public interface ServiceAbstractFactory {
	CustomerRegistryService getCustomerRegistryService();

	AccountRegistryService getAccountRegistryService();
	
	TransactionService getTransactionService(TransactionType transactionType);
}
