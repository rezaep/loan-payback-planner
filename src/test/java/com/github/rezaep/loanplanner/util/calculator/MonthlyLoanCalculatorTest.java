package com.github.rezaep.loanplanner.util.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class MonthlyLoanCalculatorTest {
    private LoanCalculator loanCalculator;

    public MonthlyLoanCalculatorTest() {
        this.loanCalculator = new MonthlyLoanCalculator();
    }

    @ParameterizedTest
    @CsvSource({
            "5000, 0.004166, 20.83",
            "100000, 0.005, 500.00",
            "25000, 0.003333, 83.32"
    })
    void shouldCalculateMonthlyInterest(BigDecimal principalBalance, BigDecimal monthlyInterestRate, BigDecimal expectedPaidInterest) {
        BigDecimal actual = loanCalculator.calculateInterest(principalBalance, monthlyInterestRate);

        assertThat(actual).isEqualTo(expectedPaidInterest);
    }

    @ParameterizedTest
    @CsvSource({
            "219.36, 20.83, 198.53",
            "219.36, 14.12, 205.24",
            "2128.75, 83.33, 2045.42",
    })
    void shouldCalculateMonthlyPrincipal(BigDecimal annuityAmount, BigDecimal paidInterest, BigDecimal expectedMonthlyPrincipal) {
        BigDecimal actual = loanCalculator.calculatePrincipal(annuityAmount, paidInterest);

        assertThat(actual).isEqualTo(expectedMonthlyPrincipal);
    }

    @ParameterizedTest
    @CsvSource({
            "5000, 0.004166, 24, 219.36",
            "100000, 0.005, 120, 1110.21",
            "25000, 0.003333, 12, 2128.75",
    })
    void shouldCalculateAnnuity(BigDecimal principalBalance, BigDecimal monthlyInterestRate, int installmentCount, BigDecimal expectedAnnuity) {
        BigDecimal actual = loanCalculator.calculateAnnuityAmount(principalBalance, monthlyInterestRate, installmentCount);

        assertThat(actual).isEqualTo(expectedAnnuity);
    }
}