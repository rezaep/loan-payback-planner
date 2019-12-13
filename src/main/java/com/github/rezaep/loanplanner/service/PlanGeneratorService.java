package com.github.rezaep.loanplanner.service;

import com.github.rezaep.loanplanner.service.model.PlanItem;
import com.github.rezaep.loanplanner.util.calculator.LoanCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanGeneratorService {
    private static final double MONTH_COUNT_IN_YEAR = 12;

    private final LoanCalculator loanCalculator;

    public List<PlanItem> generatePaybackPlan(BigDecimal principalBalance, BigDecimal annualInterestInPercent, int duration
            , ZonedDateTime startDate) {

        BigDecimal annualInterestRate = convertInterestPercentToRate(annualInterestInPercent);
        BigDecimal monthlyInterestRate = convertAnnualInterestRateToMonthly(annualInterestRate);

        BigDecimal annuity = loanCalculator.calculateAnnuityAmount(principalBalance, monthlyInterestRate, duration);

        List<PlanItem> planItems = new ArrayList<>();

        for (int month = 0; month < duration; month++) {
            PlanItem planItem = generatePlanItemForMonth(month, startDate, principalBalance, annuity, monthlyInterestRate);

            planItems.add(planItem);

            principalBalance = planItem.getRemainingOutstandingPrincipal();
        }

        return planItems;
    }

    private PlanItem generatePlanItemForMonth(int month, ZonedDateTime startDate, BigDecimal principalBalance
            , BigDecimal annuity, BigDecimal monthlyInterestRate) {
        BigDecimal paidInterest = loanCalculator.calculateInterest(principalBalance, monthlyInterestRate);
        BigDecimal paidPrincipal = loanCalculator.calculatePrincipal(annuity, paidInterest);

        if (paidPrincipal.compareTo(principalBalance) > 0) {
            paidPrincipal = principalBalance;
            // Need to recalculate the annuity
            annuity = recalculateAnnuity(paidInterest, paidPrincipal);
        }

        BigDecimal newPrincipalBalance = principalBalance.subtract(paidPrincipal);

        return new PlanItem()
                .setDate(startDate.plusMonths(month))
                .setInitialOutstandingPrincipal(principalBalance)
                .setBorrowerPaymentAmount(annuity)
                .setInterest(paidInterest)
                .setPrincipal(paidPrincipal)
                .setRemainingOutstandingPrincipal(newPrincipalBalance);
    }

    private BigDecimal convertInterestPercentToRate(BigDecimal interestInPercent) {
        return interestInPercent.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal convertAnnualInterestRateToMonthly(BigDecimal annualInterestRate) {
        return annualInterestRate.divide(BigDecimal.valueOf(MONTH_COUNT_IN_YEAR), 8, RoundingMode.HALF_EVEN);
    }

    private BigDecimal recalculateAnnuity(BigDecimal paidInterest, BigDecimal paidPrincipal) {
        return paidInterest.add(paidPrincipal);
    }
}
