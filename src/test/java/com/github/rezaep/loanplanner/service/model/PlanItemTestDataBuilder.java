package com.github.rezaep.loanplanner.service.model;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class PlanItemTestDataBuilder {

    private ZonedDateTime date;
    private BigDecimal initialOutstandingPrincipal;
    private BigDecimal borrowerPaymentAmount;
    private BigDecimal interest;
    private BigDecimal principal;
    private BigDecimal remainingOutstandingPrincipal;

    private PlanItemTestDataBuilder() {

    }

    public static PlanItemTestDataBuilder aPlanItem() {
        return new PlanItemTestDataBuilder();
    }

    public static PlanItemTestDataBuilder aValidPlanItem() {
        return new PlanItemTestDataBuilder()
                .withNowDate()
                .withInitialOutstandingPrincipal(BigDecimal.valueOf(5000.00))
                .withBorrowerPaymentAmount(BigDecimal.valueOf(219.36))
                .withInterest(BigDecimal.valueOf(20.83))
                .withPrincipal(BigDecimal.valueOf(198.53))
                .withRemainingOutstandingPrincipal(BigDecimal.valueOf(4801.47));
    }

    public PlanItemTestDataBuilder withDate(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public PlanItemTestDataBuilder withNowDate() {
        this.date = ZonedDateTime.now(ZoneId.of("UTC"));
        return this;
    }

    public PlanItemTestDataBuilder withInitialOutstandingPrincipal(BigDecimal initialOutstandingPrincipal) {
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        return this;
    }

    public PlanItemTestDataBuilder withBorrowerPaymentAmount(BigDecimal borrowerPaymentAmount) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        return this;
    }

    public PlanItemTestDataBuilder withInterest(BigDecimal interest) {
        this.interest = interest;
        return this;
    }

    public PlanItemTestDataBuilder withPrincipal(BigDecimal principal) {
        this.principal = principal;
        return this;
    }

    public PlanItemTestDataBuilder withRemainingOutstandingPrincipal(BigDecimal remainingOutstandingPrincipal) {
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
        return this;
    }

    public PlanItemTestDataBuilder but() {
        return PlanItemTestDataBuilder
                .aPlanItem()
                .withDate(date)
                .withInitialOutstandingPrincipal(initialOutstandingPrincipal)
                .withBorrowerPaymentAmount(borrowerPaymentAmount)
                .withInterest(interest)
                .withPrincipal(principal)
                .withRemainingOutstandingPrincipal(remainingOutstandingPrincipal);
    }

    public PlanItem build() {
        return new PlanItem()
                .setDate(date)
                .setInitialOutstandingPrincipal(initialOutstandingPrincipal)
                .setBorrowerPaymentAmount(borrowerPaymentAmount)
                .setInterest(interest)
                .setPrincipal(principal)
                .setRemainingOutstandingPrincipal(remainingOutstandingPrincipal);
    }
}
