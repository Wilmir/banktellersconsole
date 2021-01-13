package com.bankteller.factories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bankteller.dao.AccountDAOImpl;
import com.bankteller.dao.CustomerDAOImpl;
import com.bankteller.dao.DAOFactoryImpl;
import com.bankteller.dao.Database;

class DAOFactoryImplTest {
	private final Database database = mock(Database.class);
	private DAOFactoryImpl daoFactory;
	
	@BeforeEach
	void setUp() {
		daoFactory = new DAOFactoryImpl(database);
	}
	
	@Test
	void testGetCustomerDAO() {
		assertTrue(daoFactory.getCustomerDAO() instanceof CustomerDAOImpl);
	}
	
	@Test
	void testGetAccountDAO() {
		assertTrue(daoFactory.getAccountDAO() instanceof AccountDAOImpl);
	}

}
