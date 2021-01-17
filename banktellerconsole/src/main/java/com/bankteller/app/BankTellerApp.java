package com.bankteller.app;

import java.util.Scanner;

import com.bankteller.dao.DAOFactoryImpl;
import com.bankteller.dao.Database;
import com.bankteller.dao.MySQLDatabaseImpl;
import com.bankteller.facade.BankSystemManagerImpl;
import com.bankteller.services.ServiceFactoryImpl;
import com.bankteller.ui.ConsoleUI;

public class BankTellerApp {
    private static Scanner scanner = null;
	private final static Database database = new MySQLDatabaseImpl();

	public static void main(final String[] args) {
		connect();
 
        new ConsoleUI(new BankSystemManagerImpl(new ServiceFactoryImpl(new DAOFactoryImpl(database)))).run(scanner);
   
		disconnect();
	}


	private static void connect() {
		database.connect();
		
        scanner = new Scanner(System.in);
	}

	private static void disconnect() {
		scanner.close();
		
		database.disconnect();
	}
}
