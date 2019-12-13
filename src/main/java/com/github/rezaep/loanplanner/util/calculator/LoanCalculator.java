package com.github.rezaep.loanplanner.util.calculator;

import java.math.BigDecimal;

public interface LoanCalculator {
    BigDecimal calculateInterest(BigDecimal principalBalance, BigDecimal interestRate);

    BigDecimal calculatePrincipal(BigDecimal annuityAmount, BigDecimal paidInterest);

    BigDecimal calculateAnnuityAmount(BigDecimal principalBalance, BigDecimal interestRate, int installmentCount);
}
