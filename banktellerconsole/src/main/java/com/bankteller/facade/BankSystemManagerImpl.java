package com.bankteller.facade;

import java.time.LocalDate;

import com.bankteller.exceptions.DataAccessException;
import com.bankteller.services.CustomerRegistryService;
import com.bankteller.services.ServiceAbstractFactory;



public class BankSystemManagerImpl implements BankSystemManager{
	private final CustomerRegistryService customerRegistryService;
	
	public BankSystemManagerImpl(ServiceAbstractFactory serviceFactory) {
		customerRegistryService = serviceFactory.getCustomerRegistryService();

	}

	@Override
	public void add(final String firstName, final String lastName, final LocalDate dateOfBirth, final String address) throws DataAccessException {
		customerRegistryService.add(firstName, lastName, dateOfBirth, address);
	}

}
