package com.bankteller.facade;

import java.time.LocalDate;

import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.services.AccountRegistryService;
import com.bankteller.services.CustomerRegistryService;
import com.bankteller.services.ServiceAbstractFactory;



public class BankSystemManagerImpl implements BankSystemManager{
	private final CustomerRegistryService customerRegistryService;
	private final AccountRegistryService accountRegistryService;

	
	public BankSystemManagerImpl(final ServiceAbstractFactory serviceFactory) {
		customerRegistryService = serviceFactory.getCustomerRegistryService();
		accountRegistryService = serviceFactory.getAccountRegistryService();

	}

	@Override
	public void addCustomer(final String firstName, final String lastName, final LocalDate dateOfBirth, final String ppsNumber, final String address) throws DataAccessException, CustomerAlreadyExistsException {
		customerRegistryService.add(firstName, lastName, dateOfBirth, ppsNumber, address);
	}

	@Override
	public void addAccount(final String ppsNumber, final String accountType)
			throws DataAccessException, CustomerAlreadyExistsException, CustomerDoesNotExistException {
		accountRegistryService.add(ppsNumber, accountType);
	}

}
