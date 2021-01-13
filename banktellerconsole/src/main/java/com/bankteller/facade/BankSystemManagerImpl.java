package com.bankteller.facade;

import java.time.LocalDate;

import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.services.CustomerRegistryService;
import com.bankteller.services.ServiceAbstractFactory;



public class BankSystemManagerImpl implements BankSystemManager{
	private final CustomerRegistryService customerRegistryService;
	
	public BankSystemManagerImpl(final ServiceAbstractFactory serviceFactory) {
		customerRegistryService = serviceFactory.getCustomerRegistryService();

	}

	@Override
	public void add(final String firstName, final String lastName, final LocalDate dateOfBirth, final String ppsNumber, final String address) throws DataAccessException, CustomerAlreadyExistsException {
		customerRegistryService.add(firstName, lastName, dateOfBirth, ppsNumber, address);
	}

}
