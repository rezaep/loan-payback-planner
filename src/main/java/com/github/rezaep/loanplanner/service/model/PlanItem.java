package com.github.rezaep.loanplanner.service.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class PlanItem {
    private ZonedDateTime date;
    private BigDecimal initialOutstandingPrincipal;
    private BigDecimal borrowerPaymentAmount;
    private BigDecimal interest;
    private BigDecimal principal;
    private BigDecimal remainingOutstandingPrincipal;
}
