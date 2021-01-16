package com.bankteller.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidatorTest {
	
	@Test
	void testGetString() {
		final Scanner scanner = new Scanner("Wilmir\n");
		
		try {
			assertEquals("Wilmir", ValidationHelper.getString(scanner, "Enter your name."));
		}finally {
			scanner.close();

		}
	}
	
	@Test
	void testGetLine() {
		final Scanner scanner = new Scanner("My name is Wilmir\n");
		try {
			assertEquals("My name is Wilmir", ValidationHelper.getLine(scanner, "Please introduce yourself."));
		}finally {
			scanner.close();
		}
	}
	
	
	@Test
	void testGetInt() {
		final Scanner scanner = new Scanner("1\n");
		try {
			assertEquals(1, ValidationHelper.getInt(scanner, "What is your favorite number?"));
		}finally {
			scanner.close();
		}
	}
	
	
	@Test
	void testGetDouble() {
		final Scanner scanner = new Scanner("172.72\n");
		try {
			assertEquals(172.72, ValidationHelper.getDouble(scanner, "What is your height?"));
		}finally {
			scanner.close();
		}
	}
	
	@Test
	void testGetIntWithInitialStringInput() {
		final Scanner scanner = new Scanner("My favorite number is \n 1\n");
		
		try {
			assertEquals(1, ValidationHelper.getInt(scanner, "What is your favorite number?"));
		}finally {
			scanner.close();
		}
	}
	
	@Test
	void testGetDoubletWithInitialStringInput() {
		final Scanner scanner = new Scanner("My height in cm is \n 172.72\n");
		
		try {
			assertEquals(172.72, ValidationHelper.getDouble(scanner, "What is your height in cm?"));
		}finally {
			scanner.close();
		}
	}
	
	@ParameterizedTest(name = "The input {0} should return {1}")
	@CsvSource({"1000000, 1000000", "1000001, 1000001", "9999998, 9999998", "9999999, 9999999"})
	void testGetValidPPSNumber(final String input, final String output) {
		final Scanner scanner = new Scanner(input+"\n");
		try {
			assertEquals(output, ValidationHelper.getPPSNumber(scanner, "Enter the PPS number."));
		}finally {
			scanner.close();
		}
	}
	
	
	@Test
	void testGetPPSNumberWithInitialInvalidInput() {		
		final Scanner scanner = new Scanner("999998\n999999\n\100000000\n100000001\n7777777\n");
		try {
			assertEquals("7777777", ValidationHelper.getPPSNumber(scanner, "Enter the PPS number."));
		}finally {
			scanner.close();
		}
	}
	
	@ParameterizedTest(name = "The input {0} should return {1}")
	@CsvSource({"10000000, 10000000", "10000001, 10000001", "99999998, 99999998", "99999999, 99999999"})
	void testGetValidAccountNumber(final String input, final int output) {
		final Scanner scanner = new Scanner(input+"\n");
		try {
			assertEquals(output, ValidationHelper.getAccountNumber(scanner, "Enter the account number?"));
		}finally {
			scanner.close();
		}
	}
	
	
	@Test
	void testGetValidAccountNumberWithInitialInvalidInput() {		
		final Scanner scanner = new Scanner("9999998\n9999999\n\1000000000\n1000000001\n88888888\n");
		try {
			assertEquals(88888888, ValidationHelper.getAccountNumber(scanner, "Enter the account number?"));
		}finally {
			scanner.close();
		}
	}
	

}
