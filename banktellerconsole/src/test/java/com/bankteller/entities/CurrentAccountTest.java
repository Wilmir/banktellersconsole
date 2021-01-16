package com.bankteller.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;

class CurrentAccountTest {

	private Account account = new CurrentAccount();
	
	@BeforeEach
	void setUp() {
		account = new CurrentAccount();
	}
	
	@ParameterizedTest(name = "Same day withdrawals of {1}, and {2} do not exceed the daily limit of 10000.00 and is valid for a balance of {0}")
	@CsvFileSource(resources = "/accountwithdrawals/current/validWithdrawalAmount.csv", numLinesToSkip = 1)
	void testSuccessfulWithdrawal(final double balance, final double firstWithdrawalAmount, final double secondWithdrawalAmount, 
			final double expectedRemainingBalance, final int transactionCount) throws WithrawalLimitExceededException, NotEnoughBalanceException {
		
		account.setBalance(balance);
		
		final Transaction transaction1 = account.withdraw(firstWithdrawalAmount);
		final Transaction transaction2 = account.withdraw(secondWithdrawalAmount);
		
		assertEquals(transactionCount, account.getTransactions().size());
		assertEquals(expectedRemainingBalance, account.getBalance(), 0.001);

		assertEquals(firstWithdrawalAmount, transaction1.getAmount());
		assertEquals(secondWithdrawalAmount, transaction2.getAmount());

	}
	
	@ParameterizedTest(name = "Same day withdrawals of {1}, and {2} for a current balance of {0} is invalid")
	@CsvFileSource(resources = "/accountwithdrawals/current/invalidWithdrawalAmountZeroBalance.csv", numLinesToSkip = 1)
	void testUnSuccessfulWithdrawalDueToZeroBalance(final double balance, final double firstWithdrawalAmount, final double secondWithdrawalAmount, 
			final double expectedRemainingBalance, final int transactionCount) throws WithrawalLimitExceededException, NotEnoughBalanceException {
		
		account.setBalance(balance);
		
		final Transaction transaction1 = account.withdraw(firstWithdrawalAmount);
		final Throwable exception = assertThrows(NotEnoughBalanceException.class, () -> account.withdraw(secondWithdrawalAmount));
		
		assertEquals(transactionCount, account.getTransactions().size());
		assertEquals(expectedRemainingBalance, account.getBalance(), 0.001);

		assertEquals(firstWithdrawalAmount, transaction1.getAmount());
		assertEquals("Account does not have enough balance", exception.getMessage());

	}
	
	@ParameterizedTest(name = "Same day withdrawals of {1}, and {2} exceeds daily limit of 10000.00")
	@CsvFileSource(resources = "/accountwithdrawals/current/invalidWithdrawalAmountDailyLimit.csv", numLinesToSkip = 1)
	void testUnSuccessfulWithdrawalDueToDailyLimit(final double balance, final double firstWithdrawalAmount, final double secondWithdrawalAmount, 
			final double expectedRemainingBalance, final int transactionCount) throws WithrawalLimitExceededException, NotEnoughBalanceException {
		
		account.setBalance(balance);
		
		final Transaction transaction1 = account.withdraw(firstWithdrawalAmount);
		final Throwable exception = assertThrows(WithrawalLimitExceededException.class, () -> account.withdraw(secondWithdrawalAmount));
		
		assertEquals(transactionCount, account.getTransactions().size());
		assertEquals(expectedRemainingBalance, account.getBalance(), 0.001);

		assertEquals(firstWithdrawalAmount, transaction1.getAmount());
		assertEquals("Withdrawal limit is reached", exception.getMessage());

	}

}
