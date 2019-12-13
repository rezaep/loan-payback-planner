package com.github.rezaep.loanplanner.e2e;

import com.github.rezaep.loanplanner.controller.model.PlanRequestTestDataBuilder;
import com.github.rezaep.loanplanner.controller.model.request.PlanRequest;
import com.github.rezaep.loanplanner.controller.model.response.PlanResponseItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanPaybackPlanEndToEndTest {
    public static final String GENERATE_PLAN_URL_TEMPLATE = "/loan/plan/generate";

    private static final int DEFAULT_MONTH_COUNT = 12;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldGenerateAndConvertPaybackPlansWhenInputsAreValid() {
        PlanRequest request = PlanRequestTestDataBuilder.aRequest()
                .withLoanAmount(PlanRequestTestDataBuilder.DEFAULT_LOAN_AMOUNT)
                .withInterestRate(PlanRequestTestDataBuilder.DEFAULT_INTEREST_RATE)
                .withDuration(DEFAULT_MONTH_COUNT)
                .withStartDate(PlanRequestTestDataBuilder.START_DATE)
                .build();

        ResponseEntity<PlanResponseItem[]> generatePlanResponse = restTemplate.postForEntity(GENERATE_PLAN_URL_TEMPLATE,
                request,
                PlanResponseItem[].class);

        assertThat(generatePlanResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<PlanResponseItem> expectedPlanItems = generatePlanForTwelveMonths();
        List<PlanResponseItem> actualPlanItems = Arrays.asList(Objects.requireNonNull(generatePlanResponse.getBody()));

        assertThat(actualPlanItems).hasSize(request.getDuration());

        for (int index = 0; index < DEFAULT_MONTH_COUNT; index++) {
            PlanResponseItem expectedPlanItem = expectedPlanItems.get(index);
            PlanResponseItem actualPlanItem = actualPlanItems.get(index);

            assertThat(actualPlanItem).isEqualTo(expectedPlanItem);
        }
    }

    private List<PlanResponseItem> generatePlanForTwelveMonths() {
        return Arrays.asList(
                new PlanResponseItem()
                        .setDate(PlanRequestTestDataBuilder.START_DATE)
                        .setInitialOutstandingPrincipal(PlanRequestTestDataBuilder.DEFAULT_LOAN_AMOUNT)
                        .setBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .setInterest(new BigDecimal("20.83"))
                        .setPrincipal(new BigDecimal("407.21"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("4592.79")),
                new PlanResponseItem()
                        .setDate(LocalDate.of(2020, 2, 1).atStartOfDay(ZoneId.of("UTC")))
                        .setInitialOutstandingPrincipal(new BigDecimal("4592.79"))
                        .setBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .setInterest(new BigDecimal("19.14"))
                        .setPrincipal(new BigDecimal("408.90"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("4183.89")),
                new PlanResponseItem()
                        .setDate(LocalDate.of(2020, 3, 1).atStartOfDay(ZoneId.of("UTC")))
                        .setInitialOutstandingPrincipal(new BigDecimal("4183.89"))
                        .setBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .setInterest(new BigDecimal("17.43"))
                        .setPrincipal(new BigDecimal("410.61"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("3773.28")),
                new PlanResponseItem()
                        .setDate(LocalDate.of(2020, 4, 1).atStartOfDay(ZoneId.of("UTC")))
                        .setInitialOutstandingPrincipal(new BigDecimal("3773.28"))
                        .setBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .setInterest(new BigDecimal("15.72"))
                        .setPrincipal(new BigDecimal("412.32"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("3360.96")),
                new PlanResponseItem()
                        .setDate(LocalDate.of(2020, 5, 1).atStartOfDay(ZoneId.of("UTC")))
                        .setInitialOutstandingPrincipal(new BigDecimal("3360.96"))
                        .setBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .setInterest(new BigDecimal("14.00"))
                        .setPrincipal(new BigDecimal("414.04"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("2946.92")),
                new PlanResponseItem()
                        .setDate(LocalDate.of(2020, 6, 1).atStartOfDay(ZoneId.of("UTC")))
                        .setInitialOutstandingPrincipal(new BigDecimal("2946.92"))
                        .setBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .setInterest(new BigDecimal("12.28"))
                        .setPrincipal(new BigDecimal("415.76"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("2531.16")),
                new PlanResponseItem()
                        .setDate(LocalDate.of(2020, 7, 1).atStartOfDay(ZoneId.of("UTC")))
                        .setInitialOutstandingPrincipal(new BigDecimal("2531.16"))
                        .setBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .setInterest(new BigDecimal("10.55"))
                        .setPrincipal(new BigDecimal("417.49"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("2113.67")),
                new PlanResponseItem()
                        .setDate(LocalDate.of(2020, 8, 1).atStartOfDay(ZoneId.of("UTC")))
                        .setInitialOutstandingPrincipal(new BigDecimal("2113.67"))
                        .setBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .setInterest(new BigDecimal("8.81"))
                        .setPrincipal(new BigDecimal("419.23"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("1694.44")),
                new PlanResponseItem()
                        .setDate(LocalDate.of(2020, 9, 1).atStartOfDay(ZoneId.of("UTC")))
                        .setInitialOutstandingPrincipal(new BigDecimal("1694.44"))
                        .setBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .setInterest(new BigDecimal("7.06"))
                        .setPrincipal(new BigDecimal("420.98"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("1273.46")),
                new PlanResponseItem()
                        .setDate(LocalDate.of(2020, 10, 1).atStartOfDay(ZoneId.of("UTC")))
                        .setInitialOutstandingPrincipal(new BigDecimal("1273.46"))
                        .setBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .setInterest(new BigDecimal("5.31"))
                        .setPrincipal(new BigDecimal("422.73"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("850.73")),
                new PlanResponseItem()
                        .setDate(LocalDate.of(2020, 11, 1).atStartOfDay(ZoneId.of("UTC")))
                        .setInitialOutstandingPrincipal(new BigDecimal("850.73"))
                        .setBorrowerPaymentAmount(new BigDecimal("428.04"))
                        .setInterest(new BigDecimal("3.54"))
                        .setPrincipal(new BigDecimal("424.50"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("426.23")),
                new PlanResponseItem()
                        .setDate(LocalDate.of(2020, 12, 1).atStartOfDay(ZoneId.of("UTC")))
                        .setInitialOutstandingPrincipal(new BigDecimal("426.23"))
                        .setBorrowerPaymentAmount(new BigDecimal("428.01"))
                        .setInterest(new BigDecimal("1.78"))
                        .setPrincipal(new BigDecimal("426.23"))
                        .setRemainingOutstandingPrincipal(new BigDecimal("0.00"))
        );
    }
}
