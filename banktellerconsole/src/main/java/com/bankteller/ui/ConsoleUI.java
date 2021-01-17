package com.bankteller.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.bankteller.entities.Account;
import com.bankteller.entities.Customer;
import com.bankteller.entities.Transaction;
import com.bankteller.exceptions.AccountNotFoundException;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.CustomerDoesNotExistException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.exceptions.InvalidAmountException;
import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;
import com.bankteller.facade.BankSystemManager;

public class ConsoleUI {
	private final BankSystemManager bankSystemManager;

	public ConsoleUI(final BankSystemManager bankSystemManager) {
		this.bankSystemManager = bankSystemManager;
	}
	
	public void run(final Scanner scanner) {
        System.out.println("Welcome to the Bank Teller's Console Application\n");

		displayMainMenu();
		
		String action = "";
		while (!action.equalsIgnoreCase("exit")) {
			
			action = ValidationHelper.getString(scanner, "Enter a command: ");
			System.out.println();

			if(matchesMenuItem(action)) {
				System.out.println("\n\n\n\n" + executeMenuItem(action, scanner) + "\n");
				
			}else if(action.equalsIgnoreCase("exit")) {
				System.out.println("\n\n\n\nThank you and Good bye!\n");
				
			}else {
				System.out.println("\n\n\n\nError! Not a valid command.\n");
			}
				
			displayMainMenu();
		}
			
	}
	
	private void displayMainMenu()
    {
        System.out.println("COMMAND MENU");
        System.out.println("create 		- Register A Customer");
        System.out.println("search    	- Search Customers By Name");
        System.out.println("viewcustomer    - View A Customer");
        System.out.println("openaccount    	- Open An Account");
        System.out.println("viewaccount     - View An Account");
        System.out.println("withdraw    	- Log Withdrawals");
        System.out.println("deposit    	- Log Deposit");
        System.out.println("transfer    	- Log Money Transfer");
        System.out.println("exit	    	- Exit this application\n");
    }
	
	
	public String executeMenuItem(final String action, final Scanner scanner) {
		if(action.equalsIgnoreCase("create")) {
			return addCustomer(scanner);
			
		}else if(action.equalsIgnoreCase("search")) {
			return searchCustomerByName(scanner);
			
		}else if(action.equalsIgnoreCase("viewcustomer")) {
			return getCustomerByID(scanner);
			
		}else if(action.equalsIgnoreCase("openaccount")) {
			return openAccount(scanner);
			
		}else if(action.equalsIgnoreCase("viewaccount")) {
			return viewAccount(scanner);
			
		}else if(action.equalsIgnoreCase("withdraw")) {
			return withdraw(scanner);
			
		}else if(action.equalsIgnoreCase("deposit")) {
			return deposit(scanner);
			
		}else {
			return transferMoney(scanner);	
		}
	}

	
	public boolean matchesMenuItem(final String action) {
		final List<String> menuItems = Arrays.asList(new String[]{"create","search", "viewcustomer", "openaccount", "viewaccount", "withdraw", "deposit", "transfer"});		
		return menuItems.contains(action.toLowerCase().trim());	
	}
	
	
	public String addCustomer(final Scanner scanner) {
		final String firstName = ValidationHelper.getLine(scanner, "Enter the customer's first name: ");
		final String lastName = ValidationHelper.getLine(scanner, "Enter the customer's last name: ");
		final String ppsNumber = ValidationHelper.getPPSNumber(scanner, "Enter the customer's PPS number: ");
		final String address = ValidationHelper.getLine(scanner, "Enter the customer's address: ");
		
		try {
			bankSystemManager.addCustomer(firstName, lastName, ppsNumber, address);
			return "The new customer " + firstName + " " +  lastName + " has been added";
			
		} catch (DataAccessException e) {
			return "The bank system encountered an error.";
			
		} catch (CustomerAlreadyExistsException e) {
			return "The customer with PPS Number already exists: " 	+ ppsNumber;
			
		}
	}
	

	public String searchCustomerByName(final Scanner scanner) {
		final String firstName = ValidationHelper.getLine(scanner, "Enter the customer's first name: ");
		final String lastName = ValidationHelper.getLine(scanner, "Enter the customer's last name: ");
		
		try {
			final List<Customer> customers = bankSystemManager.getCustomers(firstName, lastName);
			
			String result = "No customer found";
			
			final int customersCount = customers.size();
			
			if(customersCount > 0) {
				result = customersCount + " customer" + (customersCount > 1 ? "s found\n" : " found\n");
				
				for(final Customer customer : customers) {
					result += customer.toString() + (customers.indexOf(customer) < (customersCount - 1) ? "\n" : "");
				}
			}
			
			return result;
			
		} catch (DataAccessException e) {
			return "The bank system encountered an error.";
		}
	}
	
	
	public String getCustomerByID(final Scanner scanner) {
		final int customerID = ValidationHelper.getInt(scanner, "Enter the customer ID: ");
		
		try {
			final Customer customer = bankSystemManager.getCustomer(customerID);
			
			String result = customer.toString();
			
			final List<Account> accounts = customer.getAccounts();
			
			if(!accounts.isEmpty()) {
				result += "\n\n" + accounts.size() + " account" + (accounts.size() > 1 ? "s found." : " found.");
			}
			
			for(final Account account : accounts) {
				result += "\n" + account.toString();
			}
			
			return result;
			
		} catch (DataAccessException e) {
			return "The bank system encountered an error.";
			
		} catch (CustomerDoesNotExistException e) {
			return "The customer with ID " + customerID + " does not exist.";
			
		}
		
	}
	
	
	public String openAccount(final Scanner scanner) {
		final String ppsNumber = ValidationHelper.getPPSNumber(scanner, "Enter the PPS number: ");
		final String accountType = ValidationHelper.getString(scanner, "Enter the account type: ").toLowerCase();
		
		
		if(!(accountType.equalsIgnoreCase("savings") || accountType.equalsIgnoreCase("current"))) {
			return "Invalid account type " + accountType;
		}
		
		try {
			bankSystemManager.addAccount(ppsNumber, accountType);
			return "The new " + accountType + " account has been added to customer with pps number: " + ppsNumber + ".";
			
		} catch (DataAccessException e) {
			return "The bank system encountered an error.";
			
		} catch (CustomerDoesNotExistException e) {
			return "The customer with pps number " + ppsNumber + " does not exist.";
			
		}
	}
	
	
	
	public String viewAccount(final Scanner scanner) {
		final int accountNumber = ValidationHelper.getAccountNumber(scanner, "Enter the 8 digit Account number: ");

		try {
			final Account account = bankSystemManager.getAccount(accountNumber);
			
			String result = account.toString();
			
			final List<Transaction> transactions = account.getTransactions();
			
			if(!transactions.isEmpty()) {
				result += "\n\nDate \t\t\tDebit\t\tCredit\t\tBalance";
			}
			
			for(final Transaction transaction : transactions) {
				result += "\n" + transaction;
			}
		
			return result;	
			
		} catch (DataAccessException e) {
			return "The bank system encountered an error.";
			
		} catch (AccountNotFoundException e) {
			return "The account does not exist " + accountNumber;
			
		}
		
	}
	
	
	public String withdraw(final Scanner scanner) {
		final int accountNumber = ValidationHelper.getAccountNumber(scanner, "Enter the 8 digit Account number: ");
		final double amount = ValidationHelper.getDouble(scanner, "Enter the amount to withdraw: ");
		
		final String formattedAmount = String.format("%.2f", amount);
		 
		return debit(accountNumber, amount, formattedAmount);
		
	}


	
	public String deposit(final Scanner scanner) {
		final int accountNumber = ValidationHelper.getAccountNumber(scanner, "Enter the 8 digit Account number: ");
		final double amount = ValidationHelper.getDouble(scanner, "Enter the amount to deposit: ");
		
		final String formattedAmount = String.format("%.2f", amount);
		 
		return credit(accountNumber, amount, formattedAmount);
	}

	
	public String transferMoney(final Scanner scanner) {
		final int sendersAccountNumber = ValidationHelper.getAccountNumber(scanner, "Enter the sender's 8 digit account number: ");
		final int receiversAccountNumber = ValidationHelper.getAccountNumber(scanner, "Enter the receiver's 8 digit account number: ");
		final double amount = ValidationHelper.getDouble(scanner, "Enter the amount to transfer: ");
		
		final String formattedAmount = String.format("%.2f", amount);	
		
		final String debitStatus = debit(sendersAccountNumber, amount, formattedAmount);
		
		if(debitStatus.equals("Successfully withdrawn " + formattedAmount + " from " + sendersAccountNumber)) {
			final String creditStatus = credit(receiversAccountNumber, amount, formattedAmount);
			
			if(creditStatus.equals("Successfully deposited " + formattedAmount + " to " + receiversAccountNumber)) {
				return debitStatus + "\n" + creditStatus;
				
			}else {
				credit(sendersAccountNumber, amount, formattedAmount);
				return creditStatus;
				
			}
		}else {
			return debitStatus;
		}
			
	}
	
	
	
	private String debit(final int accountNumber, final double amount, final String formattedAmount) {
		try {
			bankSystemManager.debit(accountNumber, amount);
			
			return "Successfully withdrawn " + formattedAmount + " from " + accountNumber;
			
		} catch (InvalidAmountException e) {

			return "Invalid amount: " + formattedAmount;
			
		} catch (DataAccessException e) {

			return "The bank system encountered an error.";
		
		} catch (AccountNotFoundException e) {

			return "The account does not exist " + accountNumber;
			
		} catch (WithrawalLimitExceededException e) {

			return "The account " + accountNumber + " will exceed daily withdrawal limit of 10000.00 euros";

		} catch (NotEnoughBalanceException e) {

			return "The account " + accountNumber + " does not have enough balance";
			
		}
	}
	
	
	
	private String credit(final int accountNumber, final double amount, final String formattedAmount) {
		try {
			bankSystemManager.credit(accountNumber, amount);
			
			return "Successfully deposited " + formattedAmount + " to " + accountNumber;
			
		} catch (InvalidAmountException e) {

			return "Invalid amount: " + formattedAmount;
			
		} catch (DataAccessException e) {

			return "The bank system encountered an error.";
		
		} catch (AccountNotFoundException e) {

			return "The account does not exist " + accountNumber;
			
		}
	}
	
}
