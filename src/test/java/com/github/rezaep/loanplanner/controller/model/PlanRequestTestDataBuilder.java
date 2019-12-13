package com.github.rezaep.loanplanner.controller.model;

import com.github.rezaep.loanplanner.controller.model.request.PlanRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class PlanRequestTestDataBuilder {
    public static final BigDecimal DEFAULT_LOAN_AMOUNT = new BigDecimal("5000.00");
    public static final BigDecimal DEFAULT_INTEREST_RATE = new BigDecimal("5.00");
    public static final int DEFAULT_DURATION = 24;
    public static final ZonedDateTime START_DATE = LocalDate.of(2020, 1, 1).atStartOfDay(ZoneId.of("UTC"));

    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private int duration;
    private ZonedDateTime startDate;

    private PlanRequestTestDataBuilder() {

    }

    public static PlanRequestTestDataBuilder aRequest() {
        return new PlanRequestTestDataBuilder();
    }

    public static PlanRequestTestDataBuilder aValidRequest() {
        return new PlanRequestTestDataBuilder()
                .withLoanAmount(DEFAULT_LOAN_AMOUNT)
                .withInterestRate(DEFAULT_INTEREST_RATE)
                .withDuration(DEFAULT_DURATION)
                .withStartDate(ZonedDateTime.now(ZoneId.of("UTC")).plusMonths(1));
    }

    public PlanRequestTestDataBuilder withLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
        return this;
    }

    public PlanRequestTestDataBuilder withInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public PlanRequestTestDataBuilder withNegativeInterestRate() {
        this.interestRate = BigDecimal.valueOf(-10);
        return this;
    }

    public PlanRequestTestDataBuilder withDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public PlanRequestTestDataBuilder withStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public PlanRequestTestDataBuilder withPastStartDate() {
        this.startDate = ZonedDateTime.now().minusDays(1);
        return this;
    }

    public PlanRequestTestDataBuilder but() {
        return PlanRequestTestDataBuilder
                .aRequest()
                .withLoanAmount(loanAmount)
                .withInterestRate(interestRate)
                .withDuration(duration)
                .withStartDate(startDate);
    }

    public PlanRequest build() {
        return new PlanRequest()
                .setLoanAmount(loanAmount)
                .setInterestRate(interestRate)
                .setDuration(duration)
                .setStartDate(startDate);
    }
}
