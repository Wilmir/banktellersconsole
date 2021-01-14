package com.bankteller.facade;

import java.time.LocalDate;

import com.bankteller.entities.Account;
import com.bankteller.entities.Customer;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;
import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;
import com.bankteller.services.AccountRegistryService;
import com.bankteller.services.CreditService;
import com.bankteller.services.CustomerRegistryService;
import com.bankteller.services.DebitService;
import com.bankteller.services.ServiceAbstractFactory;

public class BankSystemManagerImpl implements BankSystemManager{
	private final CustomerRegistryService customerRegistryService;
	private final AccountRegistryService accountRegistryService;
	private final CreditService creditService;
	private final DebitService debitService;

	
	public BankSystemManagerImpl(final ServiceAbstractFactory serviceFactory) {
		customerRegistryService = serviceFactory.getCustomerRegistryService();
		accountRegistryService = serviceFactory.getAccountRegistryService();
		creditService = serviceFactory.getCreditService();
		debitService = serviceFactory.getDebitService();

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

	
	@Override
	public void credit(final int accountNumber, final double amount)
			throws InvalidAmountException, DataAccessException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		creditService.credit(accountNumber, amount);
	}

	@Override
	public void debit(final int accountNumber, final double amount) throws InvalidAmountException, DataAccessException,
			AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		debitService.debit(accountNumber, amount);
	}
}
