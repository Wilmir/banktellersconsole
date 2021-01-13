package com.bankteller.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bankteller.entities.AccountFactory;
import com.bankteller.entities.CurrentAccount;
import com.bankteller.entities.SavingsAccount;

class AccountFactoryTest {

	private AccountFactory accountFactory;
	
	@BeforeEach
	void setUp() {
		accountFactory = new AccountFactory();
	}
	
	@Test
	void testSavingsAccountCreation() {
		assertTrue(accountFactory.getAccount("savings") instanceof SavingsAccount);
	}
	
	@Test
	void testCurrentAccountCreation() {
		assertTrue(accountFactory.getAccount("current") instanceof CurrentAccount);
	}

}
