package com.bankteller.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
	void connect();
	void disconnect() throws SQLException;
	Connection getConnection();

}
