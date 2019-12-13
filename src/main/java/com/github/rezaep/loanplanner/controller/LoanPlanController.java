package com.github.rezaep.loanplanner.controller;

import com.github.rezaep.loanplanner.controller.model.request.PlanRequest;
import com.github.rezaep.loanplanner.controller.model.response.PlanResponseItem;
import com.github.rezaep.loanplanner.service.PlanGeneratorService;
import com.github.rezaep.loanplanner.service.model.PlanItem;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("loan/plan")
@RequiredArgsConstructor
public class LoanPlanController {
    private final PlanGeneratorService planGenerator;

    @PostMapping("generate")
    public List<PlanResponseItem> generatePaybackPlan(@RequestBody @Valid PlanRequest request) {
        return planGenerator.generatePaybackPlan(request.getLoanAmount(), request.getInterestRate()
                , request.getDuration(), request.getStartDate())
                .stream()
                .map(this::convertToPlanResponseItem)
                .collect(Collectors.toList());
    }

    private PlanResponseItem convertToPlanResponseItem(PlanItem planItem) {
        return new PlanResponseItem()
                .setDate(planItem.getDate())
                .setInitialOutstandingPrincipal(planItem.getInitialOutstandingPrincipal())
                .setBorrowerPaymentAmount(planItem.getBorrowerPaymentAmount())
                .setInterest(planItem.getInterest())
                .setPrincipal(planItem.getPrincipal())
                .setRemainingOutstandingPrincipal(planItem.getRemainingOutstandingPrincipal());
    }
}
