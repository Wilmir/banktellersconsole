package com.bankteller.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;
import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;
import com.bankteller.facade.BankSystemManager;

class BankConsoleUITest {
	private final BankSystemManager bankSystemManager = mock(BankSystemManager.class);
	private ConsoleUI bankUI;
	
	@BeforeEach
	void setUp() {
		bankUI = new ConsoleUI(bankSystemManager);
	}	
	
	
	@ParameterizedTest
	@CsvSource({"wilmir,nicanor,1234567,Dublin Ireland"})
	void testUnsuccessfulAdditionOfCustomerDuetoDataAccessException(final String firstName, final String lastName, final String ppsNumber,final String address) throws DataAccessException, CustomerAlreadyExistsException {
		final String input = firstName + "\n" + lastName + "\n" + ppsNumber + "\n" + address + "\n";
		final Scanner scanner = new Scanner(input);
		try {
			when(bankSystemManager.addCustomer(firstName, lastName, ppsNumber, address)).thenThrow(DataAccessException.class);
			assertEquals("The bank system encountered an error.", bankUI.addCustomer(scanner));
		}finally {
			scanner.close();
		}

	}
	

	@ParameterizedTest
	@CsvSource({"1234567, savings", "7777777, current"})
	void testUnsuccessfulAdditionOfAccountDuetoDataAccessException(final String ppsNumber, final String accountType) throws DataAccessException, CustomerDoesNotExistException {
		final String input = ppsNumber + "\n" + accountType + "\n";
		final Scanner scanner = new Scanner(input);		
		try {
			when(bankSystemManager.addAccount(ppsNumber, accountType)).thenThrow(DataAccessException.class);
			assertEquals("The bank system encountered an error.", bankUI.openAccount(scanner));
		}finally {
			scanner.close();
		}

	}
	
	@ParameterizedTest
	@CsvSource({"12345678, 300.00"})
	void testUnsuccessfulWithdrawalDuetoDataAccessException(final int accountNumber, final double amount) throws DataAccessException, InvalidAmountException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		final String input = accountNumber + "\n" + amount + "\n";
		final Scanner scanner = new Scanner(input);
		try {
			doThrow(DataAccessException.class).when(bankSystemManager).debit(accountNumber, amount);
			assertEquals("The bank system encountered an error.", bankUI.withdraw(scanner));
		}finally {
			scanner.close();
		}

	}
	
	
	@ParameterizedTest
	@CsvSource({"12345678, 300.00"})
	void testUnsuccessfulDepositDuetoDataAccessException(final int accountNumber, final double amount) throws DataAccessException, InvalidAmountException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		final String input = accountNumber + "\n" + amount + "\n";
		final Scanner scanner = new Scanner(input);
	
		try {
			doThrow(DataAccessException.class).when(bankSystemManager).credit(accountNumber, amount);
			assertEquals("The bank system encountered an error.", bankUI.deposit(scanner));
		}finally {
			scanner.close();
		}

	}

	@ParameterizedTest
	@CsvSource({"rhea, nicanor"})
	void testUnsuccessfulSearchByNameDuetoDataAccessException(final String firstName, final String lastName) throws DataAccessException{
		final String input = firstName + "\n" + lastName + "\n";
		final Scanner scanner = new Scanner(input);		
		try {
			doThrow(DataAccessException.class).when(bankSystemManager).getCustomers(firstName, lastName);
			assertEquals("The bank system encountered an error.", bankUI.searchCustomerByName(scanner));
		}finally {
			scanner.close();
		}

	}
	
	
	@ParameterizedTest
	@CsvSource({"12345678"})
	void testUnsuccessfulViewAccountDuetoDataAccessException(final int accountNumber) throws DataAccessException, InvalidAmountException, AccountNotFoundException{
		final String input = accountNumber + "\n";
		final Scanner scanner = new Scanner(input);		
		
		try {
			doThrow(DataAccessException.class).when(bankSystemManager).getAccount(accountNumber);
			assertEquals("The bank system encountered an error.", bankUI.viewAccount(scanner));
		} finally {
			scanner.close();
		}

	}
	
	@ParameterizedTest
	@CsvSource({"98765432"})
	void testUnsuccessfulViewCustomerDuetoDataAccessException(final int customerID) throws DataAccessException, CustomerDoesNotExistException {
		final String input = customerID + "\n";
		final Scanner scanner = new Scanner(input);
		
		try {
			doThrow(DataAccessException.class).when(bankSystemManager).getCustomer(customerID);
			assertEquals("The bank system encountered an error.", bankUI.getCustomerByID(scanner));
		}finally {
			scanner.close();
		}
	}
	
}
