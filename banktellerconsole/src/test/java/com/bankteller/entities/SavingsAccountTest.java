package com.bankteller.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.bankteller.exceptions.NotEnoughBalanceException;
import com.bankteller.exceptions.WithrawalLimitExceededException;

class SavingsAccountTest {

	private Account account;

	@BeforeEach
	void setUp() {
		account = new SavingsAccount();
	}

	@ParameterizedTest(name = "Same day withdrawals of {1}, and {2} is valid for a balance of {0}")
	@CsvFileSource(resources = "/accountwithdrawals/savings/validWithdrawalAmount.csv", numLinesToSkip = 1)
	void testSuccessfulWithdrawal(final double balance, final double firstWithdrawalAmount, final double secondWithdrawalAmount, 
			final double expectedRemainingBalance, final int transactionCount) throws WithrawalLimitExceededException, NotEnoughBalanceException {

		account.setBalance(balance);

		final Transaction transaction1 = account.withdraw(firstWithdrawalAmount);
		final Transaction transaction2 = account.withdraw(secondWithdrawalAmount);

		assertEquals(transactionCount, account.getTransactions().size());
		assertEquals(expectedRemainingBalance, account.getBalance(), 0.001);

		assertEquals(firstWithdrawalAmount, transaction1.getAmount());
		assertEquals(secondWithdrawalAmount, transaction2.getAmount());
		assertEquals(account.getBalance(), transaction2.getPostTransactionBalance(), 0.001);


	}

	@ParameterizedTest(name = "Same day withdrawals of {1}, and {2} for a current balance of {0} is invalid")
	@CsvFileSource(resources = "/accountwithdrawals/savings/invalidWithdrawalAmountZeroBalance.csv", numLinesToSkip = 1)
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

}
