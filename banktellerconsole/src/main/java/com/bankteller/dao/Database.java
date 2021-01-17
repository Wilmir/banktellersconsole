package com.bankteller.dao;

import java.sql.Connection;

public interface Database {
	void connect();
	void disconnect();
	Connection getConnection();

}
