package com.github.rezaep.loanplanner.controller.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class PlanRequest {
    private BigDecimal loanAmount;
    @Positive
    private BigDecimal interestRate;
    @Positive
    private int duration;
    @NotNull
    private ZonedDateTime startDate;
}
