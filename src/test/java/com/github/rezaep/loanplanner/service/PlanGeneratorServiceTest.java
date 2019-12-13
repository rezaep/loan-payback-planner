package com.github.rezaep.loanplanner.service;

import com.github.rezaep.loanplanner.controller.model.PlanRequestTestDataBuilder;
import com.github.rezaep.loanplanner.service.model.PlanItem;
import com.github.rezaep.loanplanner.util.calculator.LoanCalculator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PlanGeneratorServiceTest {
    private static final BigDecimal TRIVIAL_BIG_DECIMAL = BigDecimal.ONE;

    @Mock
    private LoanCalculator loanCalculator;

    @InjectMocks
    private PlanGeneratorService generatorService;

    @ParameterizedTest
    @ValueSource(ints = {1, 6, 12, 24, 120})
    void shouldGeneratePlanListWithSizeEqualToTheGivenDuration(int duration) {
        given(loanCalculator.calculateInterest(any(), any())).willReturn(TRIVIAL_BIG_DECIMAL);
        given(loanCalculator.calculatePrincipal(any(), any())).willReturn(TRIVIAL_BIG_DECIMAL);
        given(loanCalculator.calculateAnnuityAmount(any(), any(), anyInt())).willReturn(TRIVIAL_BIG_DECIMAL);

        List<PlanItem> actualPlanItems = generatorService.generatePaybackPlan(PlanRequestTestDataBuilder.DEFAULT_LOAN_AMOUNT
                , PlanRequestTestDataBuilder.DEFAULT_INTEREST_RATE
                , duration, ZonedDateTime.now(ZoneOffset.UTC));

        assertThat(actualPlanItems).hasSize(duration);
    }
}