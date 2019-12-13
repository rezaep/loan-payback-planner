package com.github.rezaep.loanplanner.util.calculator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class MonthlyLoanCalculator implements LoanCalculator {

    @Override
    public BigDecimal calculateInterest(BigDecimal principalBalance, BigDecimal interestRate) {
        return principalBalance.multiply(interestRate).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal calculatePrincipal(BigDecimal annuityAmount, BigDecimal paidInterest) {
        return annuityAmount.subtract(paidInterest).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal calculateAnnuityAmount(BigDecimal principalBalance, BigDecimal interestRate, int installmentCount) {
//        (principalBalance * interestRate) / (1 - 1 / Math.pow(1 + interestRate, installmentCount))
        BigDecimal numerator = principalBalance.multiply(interestRate);
        BigDecimal power = interestRate.add(BigDecimal.ONE).pow(installmentCount).setScale(8, RoundingMode.HALF_EVEN);
        BigDecimal reverse = BigDecimal.ONE.divide(power, 8, RoundingMode.HALF_EVEN);
        BigDecimal denominator = BigDecimal.ONE.subtract(reverse);
        return numerator.divide(denominator, 2, RoundingMode.UP);
    }
}
