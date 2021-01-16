package com.bankteller.facade;

import java.util.List;

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
	public Customer addCustomer(final String firstName, final String lastName, final String ppsNumber, final String address) throws DataAccessException, CustomerAlreadyExistsException {
		return customerRegistryService.add(firstName, lastName, ppsNumber, address);
	}

	@Override
	public List<Customer> getCustomers(final String firstName, final String lastName) throws DataAccessException {
		return customerRegistryService.getCustomers(firstName, lastName);
	}
	
	@Override
	public Customer getCustomer(final int customerID) throws DataAccessException, CustomerDoesNotExistException {
		return customerRegistryService.getCustomer(customerID);
	}
	
	@Override
	public Account addAccount(final String ppsNumber, final String accountType)
			throws DataAccessException, CustomerDoesNotExistException {
		return accountRegistryService.add(ppsNumber, accountType);
	}
	
	@Override
	public Account getAccount(final int accountNumber) throws DataAccessException, AccountNotFoundException {
		return accountRegistryService.getAccount(accountNumber);
	}
	
	@Override
	public void credit(final int accountNumber, final double amount)
			throws InvalidAmountException, DataAccessException, AccountNotFoundException{
		creditService.credit(accountNumber, amount);
	}

	@Override
	public void debit(final int accountNumber, final double amount) throws InvalidAmountException, DataAccessException,
			AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		debitService.debit(accountNumber, amount);
	}


}
