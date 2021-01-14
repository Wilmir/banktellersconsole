package com.bankteller.factories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bankteller.dao.DAOAbstractFactory;
import com.bankteller.entities.TransactionType;
import com.bankteller.services.AccountRegistryServiceImpl;
import com.bankteller.services.CreditServiceImpl;
import com.bankteller.services.CustomerRegistryServiceImpl;
import com.bankteller.services.ServiceAbstractFactory;
import com.bankteller.services.ServiceFactoryImpl;

class ServiceFactoryImplTest {
	private final DAOAbstractFactory daoFactory = mock(DAOAbstractFactory.class);
	private ServiceAbstractFactory serviceFactory;
	
	@BeforeEach
	void setUp() {
		serviceFactory = new ServiceFactoryImpl(daoFactory);
	}
	
	@Test
	void testGetCustomerDAO() {
		assertTrue(serviceFactory.getCustomerRegistryService() instanceof CustomerRegistryServiceImpl);
	}
	
	@Test
	void testGetAccountDAO() {
		assertTrue(serviceFactory.getAccountRegistryService() instanceof AccountRegistryServiceImpl);
	}
	
	@Test 
	void testGetCreditTransactionService(){
		assertTrue(serviceFactory.getTransactionService(TransactionType.CREDIT) instanceof CreditServiceImpl);
	}

}
