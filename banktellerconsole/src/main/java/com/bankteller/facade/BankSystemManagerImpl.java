package com.bankteller.facade;

import java.time.LocalDate;

import com.bankteller.entities.Account;
import com.bankteller.entities.Customer;
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
	public Customer addCustomer(final String firstName, final String lastName, final LocalDate dateOfBirth, final String ppsNumber, final String address) throws DataAccessException, CustomerAlreadyExistsException {
		return customerRegistryService.add(firstName, lastName, dateOfBirth, ppsNumber, address);
	}

	@Override
	public Account addAccount(final String ppsNumber, final String accountType)
			throws DataAccessException, CustomerAlreadyExistsException, CustomerDoesNotExistException {
		return accountRegistryService.add(ppsNumber, accountType);
	}

}
