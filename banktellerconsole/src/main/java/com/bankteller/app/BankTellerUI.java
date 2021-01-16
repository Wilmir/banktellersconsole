package com.bankteller.app;

import java.util.Scanner;

import com.bankteller.dao.DAOFactoryImpl;
import com.bankteller.dao.MySQLDatabaseImpl;
import com.bankteller.exceptions.CustomerAlreadyExistsException;
import com.bankteller.exceptions.DataAccessException;
import com.bankteller.facade.BankSystemManager;
import com.bankteller.facade.BankSystemManagerImpl;
import com.bankteller.services.ServiceFactoryImpl;
import com.bankteller.ui.ValidationHelper;

public class BankTellerUI {
    private static Scanner scanner = null;
	
	public static void main(final String[] args) {
        System.out.println("Welcome to the Bank Teller's Console Application\n");

        scanner = new Scanner(System.in);

        displayMenu();

        String action = "";
		while (!action.equalsIgnoreCase("exit")) {
			action = ValidationHelper.getString(scanner, "Enter a command: ");
			System.out.println();

			if (action.equalsIgnoreCase("register")) {
				registerACustomer();
			} else if (action.equalsIgnoreCase("exit")) {
				System.out.println("Bye.\n");
			} else {
				System.out.println("Error! Not a valid command.\n");
			}
		}
		scanner.close();
	}
	
    private static void registerACustomer() {
	    final String firstName = ValidationHelper.getLine(scanner, "Enter employee firstName: ");
	    final String lastName = ValidationHelper.getLine(scanner, "Enter employee lastName: ");
	    final String ppsNumber = ValidationHelper.getString(scanner, "Enter ppsNumber: ");
	    final String address = ValidationHelper.getLine(scanner, "Enter address: ");
	    
	    final BankSystemManager bankSystemManager= new BankSystemManagerImpl(new ServiceFactoryImpl(new DAOFactoryImpl(new MySQLDatabaseImpl())));
	    
	    try {
			bankSystemManager.addCustomer(firstName, lastName, ppsNumber, address);
		} catch (DataAccessException e) {
			System.out.println("Our system encountered an error please try again.");
		} catch (CustomerAlreadyExistsException e) {
			System.out.println("Our system encountered an error please try again.");
		}

	}


	public static void displayMenu()
    {
        System.out.println("COMMAND MENU");
        System.out.println("register - Register A Customer");
        System.out.println("open    - Open A Bank Account");
        System.out.println("help    - Show this menu");
        System.out.println("exit    - Exit this application\n");
    }
	

}
