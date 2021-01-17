package com.bankteller.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;
import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;
import com.bankteller.facade.BankSystemManager;

class BankConsoleUnitTest {
	private final BankSystemManager bankSystemManager = mock(BankSystemManager.class);
	private ConsoleUI bankUI;
	
	@BeforeEach
	void setUp() {
		bankUI = new ConsoleUI(bankSystemManager);
	}	
	
	@ParameterizedTest(name = ("{0} is a valid menu item input"))
	@ValueSource(strings = {"  cReAtE   ","searcH", "viewCustomer", "Openaccount", "viewAccount", "withdraw", "depOsit", "tRansfer", "create","search", "viewcustomer", "openaccount", "viewaccount", "withdraw", "deposit", "transfer"})
	void testValidMenuInputs(final String menuInput) {
		assertTrue(bankUI.matchesMenuItem(menuInput));
	}
	
	
	@ParameterizedTest(name = ("{0} is an invalid menu item input"))
	@ValueSource(strings = {"cReAtOR","sea rcH", "v1ewCustomer", "opn", "vie w", "with draw", "dep0sit", "12345", "", "\n"})
	void testInValidMenuInputs(final String menuInput) {
		assertFalse(bankUI.matchesMenuItem(menuInput));
	}
	
	
	@ParameterizedTest
	@CsvSource({"create, wilmir,nicanor,1234567,Dublin Ireland"})
	void testUnsuccessfulAdditionOfCustomerDuetoDataAccessException(final String action, final String firstName, final String lastName, final String ppsNumber,final String address) throws DataAccessException, CustomerAlreadyExistsException {
		final String input = firstName + "\n" + lastName + "\n" + ppsNumber + "\n" + address + "\n";
		final Scanner scanner = new Scanner(input);
		try {
			when(bankSystemManager.addCustomer(firstName, lastName, ppsNumber, address)).thenThrow(DataAccessException.class);
			assertEquals("The bank system encountered an error.", bankUI.executeMenuItem(action, scanner));
			verify(bankSystemManager, times(1)).addCustomer(firstName, lastName, ppsNumber, address);
			verifyNoMoreInteractions(bankSystemManager);
		}finally {
			scanner.close();
		}

	}

	

	@ParameterizedTest
	@CsvSource({"openaccount,1234567,savings", "openaccount,7777777,current"})
	void testUnsuccessfulAdditionOfAccountDuetoDataAccessException(final String action, final String ppsNumber, final String accountType) throws DataAccessException, CustomerDoesNotExistException {
		final String input = ppsNumber + "\n" + accountType + "\n";
		final Scanner scanner = new Scanner(input);		
		try {
			when(bankSystemManager.addAccount(ppsNumber, accountType)).thenThrow(DataAccessException.class);
			assertEquals("The bank system encountered an error.", bankUI.executeMenuItem(action, scanner));
			verify(bankSystemManager, times(1)).addAccount(ppsNumber, accountType);
			verifyNoMoreInteractions(bankSystemManager);
		}finally {
			scanner.close();
		}

	}
	
	@ParameterizedTest
	@CsvSource({"withdraw, 12345678, 300.00"})
	void testUnsuccessfulWithdrawalDuetoDataAccessException(final String action, final int accountNumber, final double amount) throws DataAccessException, InvalidAmountException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		final String input = accountNumber + "\n" + amount + "\n";
		final Scanner scanner = new Scanner(input);
		try {
			doThrow(DataAccessException.class).when(bankSystemManager).debit(accountNumber, amount);
			assertEquals("The bank system encountered an error.", bankUI.executeMenuItem(action, scanner));
			verify(bankSystemManager, times(1)).debit(accountNumber, amount);
			verifyNoMoreInteractions(bankSystemManager);
		}finally {
			scanner.close();
		}

	}
	
	
	@ParameterizedTest
	@CsvSource({"deposit, 12345678, 300.00"})
	void testUnsuccessfulDepositDuetoDataAccessException(final String action, final int accountNumber, final double amount) throws DataAccessException, InvalidAmountException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		final String input = accountNumber + "\n" + amount + "\n";
		final Scanner scanner = new Scanner(input);
	
		try {
			doThrow(DataAccessException.class).when(bankSystemManager).credit(accountNumber, amount);
			assertEquals("The bank system encountered an error.", bankUI.executeMenuItem(action, scanner));
			verify(bankSystemManager, times(1)).credit(accountNumber, amount);
			verifyNoMoreInteractions(bankSystemManager);
		}finally {
			scanner.close();
		}

	}
	
	@ParameterizedTest
	@CsvSource({"transfer, 12345678, 87654321, 300.00"})
	void testUnsuccessfulTransferDuetoDataAccessException(final String action, final int sendersAccountNumber, final int receiversAccountNumber, final double amount) throws DataAccessException, InvalidAmountException, AccountNotFoundException, WithrawalLimitExceededException, NotEnoughBalanceException {
		final String input = sendersAccountNumber + "\n"  + receiversAccountNumber + "\n" + amount + "\n";
		final Scanner scanner = new Scanner(input);
	
		try {
			doThrow(DataAccessException.class).when(bankSystemManager).credit(receiversAccountNumber, amount);
			assertEquals("The bank system encountered an error.", bankUI.executeMenuItem(action, scanner));
			verify(bankSystemManager, times(1)).debit(sendersAccountNumber, amount);
			verify(bankSystemManager, times(1)).credit(receiversAccountNumber, amount);
			verify(bankSystemManager, times(1)).credit(sendersAccountNumber, amount);
			verifyNoMoreInteractions(bankSystemManager);

		}finally {
			scanner.close();
		}

	}

	@ParameterizedTest
	@CsvSource({"search, rhea, nicanor"})
	void testUnsuccessfulSearchByNameDuetoDataAccessException(final String action, final String firstName, final String lastName) throws DataAccessException{
		final String input = firstName + "\n" + lastName + "\n";
		final Scanner scanner = new Scanner(input);		
		try {
			doThrow(DataAccessException.class).when(bankSystemManager).getCustomers(firstName, lastName);
			assertEquals("The bank system encountered an error.", bankUI.executeMenuItem(action, scanner));
			verify(bankSystemManager, times(1)).getCustomers(firstName, lastName);
			verifyNoMoreInteractions(bankSystemManager);
		}finally {
			scanner.close();
		}

	}
	
	
	@ParameterizedTest
	@CsvSource({"viewaccount, 12345678"})
	void testUnsuccessfulViewAccountDuetoDataAccessException(final String action, final int accountNumber) throws DataAccessException, InvalidAmountException, AccountNotFoundException{
		final String input = accountNumber + "\n";
		final Scanner scanner = new Scanner(input);		
		
		try {
			doThrow(DataAccessException.class).when(bankSystemManager).getAccount(accountNumber);
			assertEquals("The bank system encountered an error.", bankUI.executeMenuItem(action, scanner));
			verify(bankSystemManager, times(1)).getAccount(accountNumber);
			verifyNoMoreInteractions(bankSystemManager);
		} finally {
			scanner.close();
		}

	}
	
	@ParameterizedTest
	@CsvSource({"viewcustomer, 98765432"})
	void testUnsuccessfulViewCustomerDuetoDataAccessException(final String action, final int customerID) throws DataAccessException, CustomerDoesNotExistException {
		final String input = customerID + "\n";
		final Scanner scanner = new Scanner(input);
		
		try {
			doThrow(DataAccessException.class).when(bankSystemManager).getCustomer(customerID);
			assertEquals("The bank system encountered an error.", bankUI.executeMenuItem(action, scanner));
			verify(bankSystemManager, times(1)).getCustomer(customerID);
			verifyNoMoreInteractions(bankSystemManager);
		}finally {
			scanner.close();
		}
	}
	
}
