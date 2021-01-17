package com.bankteller.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TransactionTest {
	
	@Test
	void testPrintingDebitTransaction() {
		final Transaction transaction = new Transaction(true, 99.99, 100.00);
		assertEquals(LocalDate.now() + "		99.99				100.00", transaction.toString());
	}
	
	@Test
	void testPrintingCreditTransaction() {
		final Transaction transaction = new Transaction(false, 99.99, 100.00);
		assertEquals(LocalDate.now() + "				99.99		100.00", transaction.toString());
	}
}
