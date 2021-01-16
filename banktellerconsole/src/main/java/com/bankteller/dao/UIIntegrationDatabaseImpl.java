package com.bankteller.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UIIntegrationDatabaseImpl implements Database{
	private Connection connection;
	private final static String URL = "jdbc:mysql://localhost:3306/uiintegrationtest_bankteller";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "ilovemyself";
	
	public void connect(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Failed to connect to DB");
		} 
	}
		
	public void disconnect(){
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Failed to disconnect to DB");
		}
	}

	public Connection getConnection() {
		return connection;
	}	
}
