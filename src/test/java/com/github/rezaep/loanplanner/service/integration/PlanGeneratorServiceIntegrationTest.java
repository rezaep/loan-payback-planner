package com.github.rezaep.loanplanner.service.integration;

import com.github.rezaep.loanplanner.controller.model.PlanRequestTestDataBuilder;
import com.github.rezaep.loanplanner.service.PlanGeneratorService;
import com.github.rezaep.loanplanner.service.model.PlanItem;
import com.github.rezaep.loanplanner.service.model.PlanItemTestDataBuilder;
import com.github.rezaep.loanplanner.util.calculator.MonthlyLoanCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {PlanGeneratorService.class, MonthlyLoanCalculator.class})
class PlanGeneratorServiceIntegrationTest {
    private static final int DEFAULT_MONTH_COUNT = 12;

    private static final ZonedDateTime START_DATE = LocalDate.of(2020, 1, 1).atStartOfDay(ZoneId.of("UTC"));

    @Autowired
    private PlanGeneratorService generatorService;

    @Test
    void shouldGeneratePlanListWhenInputsAreValid() {
        List<PlanItem> expectedPlanItems = generatePlanForTwelveMonths();
        List<PlanItem> actualPlanItems = generatorService.generatePaybackPlan(PlanRequestTestDataBuilder.DEFAULT_LOAN_AMOUNT
                , PlanRequestTestDataBuilder.DEFAULT_INTEREST_RATE, DEFAULT_MONTH_COUNT, START_DATE);

        for (int index = 0; index < DEFAULT_MONTH_COUNT; index++) {
            PlanItem expectedPlanItem = expectedPlanItems.get(index);
            PlanItem actualPlanItem = actualPlanItems.get(index);

            assertThat(actualPlanItem).isEqualTo(expectedPlanItem);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 6, 12, 24})
    void shouldGeneratedPlanStartsWithTheGivenStartDate(int durationInMonth) {
        List<PlanItem> actualPlanItems = generatorService.generatePaybackPlan(PlanRequestTestDataBuilder.DEFAULT_LOAN_AMOUNT
                , PlanRequestTestDataBuilder.DEFAULT_INTEREST_RATE, durationInMonth, START_DATE);

        PlanItem firstItem = actualPlanItems.get(0);

        assertThat(firstItem.getDate()).isEqualToIgnoringHours(START_DATE);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 6, 12, 24})
    void shouldGeneratedPlanEndsWithTheGivenStartDatePlusDurationInMonth(int durationInMonth) {
        List<PlanItem> actualPlanItems = generatorService.generatePaybackPlan(PlanRequestTestDataBuilder.DEFAULT_LOAN_AMOUNT
                , PlanRequestTestDataBuilder.DEFAULT_INTEREST_RATE, durationInMonth, START_DATE);

        ZonedDateTime expectedEndDate = START_DATE.plusMonths(durationInMonth - 1); // Ignore first month
        PlanItem lastItem = actualPlanItems.get(actualPlanItems.size() - 1);

        assertThat(lastItem.getDate()).isEqualToIgnoringHours(expectedEndDate);
    }

    @Test
    void shouldGeneratedPlanInitialPrincipalBeEqualToTheGivenPrincipalBalance() {
        List<PlanItem> actualPlanItems = generatorService.generatePaybackPlan(PlanRequestTestDataBuilder.DEFAULT_LOAN_AMOUNT
                , PlanRequestTestDataBuilder.DEFAULT_INTEREST_RATE, DEFAULT_MONTH_COUNT, START_DATE);

        PlanItem firstItem = actualPlanItems.get(0);

        assertThat(firstItem.getInitialOutstandingPrincipal()).isEqualTo(PlanRequestTestDataBuilder.DEFAULT_LOAN_AMOUNT);
    }

    @Test
    void shouldRemainingPrincipalBalanceOfLastItemOfGeneratedPlanBeEqualToTheZero() {
        List<PlanItem> actualPlanItems = generatorService.generatePaybackPlan(PlanRequestTestDataBuilder.DEFAULT_LOAN_AMOUNT
                , PlanRequestTestDataBuilder.DEFAULT_INTEREST_RATE, DEFAULT_MONTH_COUNT, START_DATE);

        PlanItem lastItem = actualPlanItems.get(actualPlanItems.size() - 1);

        assertThat(lastItem.getRemainingOutstandingPrincipal()).isEqualTo(new BigDecimal("0.00"));
    }

    private List<PlanItem> generatePlanForTwelveMonths() {
        return Arrays.asList(
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 1, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(PlanRequestTestDataBuilder.DEFAULT_LOAN_AMOUNT)
                        .withBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .withInterest(new BigDecimal("20.83"))
                        .withPrincipal(new BigDecimal("407.21"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("4592.79"))
                        .build(),
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 2, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(new BigDecimal("4592.79"))
                        .withBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .withInterest(new BigDecimal("19.14"))
                        .withPrincipal(new BigDecimal("408.90"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("4183.89"))
                        .build(),
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 3, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(new BigDecimal("4183.89"))
                        .withBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .withInterest(new BigDecimal("17.43"))
                        .withPrincipal(new BigDecimal("410.61"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("3773.28"))
                        .build(),
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 4, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(new BigDecimal("3773.28"))
                        .withBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .withInterest(new BigDecimal("15.72"))
                        .withPrincipal(new BigDecimal("412.32"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("3360.96"))
                        .build(),
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 5, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(new BigDecimal("3360.96"))
                        .withBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .withInterest(new BigDecimal("14.00"))
                        .withPrincipal(new BigDecimal("414.04"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("2946.92"))
                        .build(),
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 6, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(new BigDecimal("2946.92"))
                        .withBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .withInterest(new BigDecimal("12.28"))
                        .withPrincipal(new BigDecimal("415.76"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("2531.16"))
                        .build(),
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 7, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(new BigDecimal("2531.16"))
                        .withBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .withInterest(new BigDecimal("10.55"))
                        .withPrincipal(new BigDecimal("417.49"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("2113.67"))
                        .build(),
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 8, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(new BigDecimal("2113.67"))
                        .withBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .withInterest(new BigDecimal("8.81"))
                        .withPrincipal(new BigDecimal("419.23"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("1694.44"))
                        .build(),
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 9, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(new BigDecimal("1694.44"))
                        .withBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .withInterest(new BigDecimal("7.06"))
                        .withPrincipal(new BigDecimal("420.98"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("1273.46"))
                        .build(),
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 10, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(new BigDecimal("1273.46"))
                        .withBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .withInterest(new BigDecimal("5.31"))
                        .withPrincipal(new BigDecimal("422.73"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("850.73"))
                        .build(),
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 11, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(new BigDecimal("850.73"))
                        .withBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .withInterest(new BigDecimal("3.54"))
                        .withPrincipal(new BigDecimal("424.50"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("426.23"))
                        .build(),
                PlanItemTestDataBuilder.aPlanItem()
                        .withDate(LocalDate.of(2020, 12, 1).atStartOfDay(ZoneId.of("UTC")))
                        .withInitialOutstandingPrincipal(new BigDecimal("426.23"))
                        .withBorrowerPaymentAmount(new BigDecimal("428.01"))
                        .withInterest(new BigDecimal("1.78"))
                        .withPrincipal(new BigDecimal("426.23"))
                        .withRemainingOutstandingPrincipal(new BigDecimal("0.00"))
                        .build()
        );
    }
}