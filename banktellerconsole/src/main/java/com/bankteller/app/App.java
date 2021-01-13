package com.bankteller.app;

import com.bankteller.dao.Database;
import com.bankteller.dao.MySQLDatabaseImpl;

public class App {
	public static void main(String[] args) {
		Database database = new MySQLDatabaseImpl();
		database.connect();
	}
}
