package com.github.rezaep.loanplanner.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rezaep.loanplanner.controller.model.PlanRequestTestDataBuilder;
import com.github.rezaep.loanplanner.controller.model.request.PlanRequest;
import com.github.rezaep.loanplanner.controller.model.response.PlanResponseItem;
import com.github.rezaep.loanplanner.service.PlanGeneratorService;
import com.github.rezaep.loanplanner.service.model.PlanItem;
import com.github.rezaep.loanplanner.service.model.PlanItemTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanPlanController.class)
public class LoanPlanControllerTest {
    public static final String GENERATE_PLAN_URL_TEMPLATE = "/loan/plan/generate";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanGeneratorService planGeneratorService;

    @Test
    void shouldGetHttpStatus200WhenRequestIsValid() throws Exception {
        PlanRequest request = PlanRequestTestDataBuilder.aValidRequest().build();

        mockMvc.perform(post(GENERATE_PLAN_URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGeneratePlanListWithSizeEqualToGivenDurationWhenRequestIsValid() throws Exception {
        int duration = 24;

        PlanRequest request = PlanRequestTestDataBuilder.aValidRequest()
                .withDuration(duration)
                .build();

        List<PlanItem> somePlanItems = generateSomePlanItems(duration);

        given(planGeneratorService.generatePaybackPlan(request.getLoanAmount(), request.getInterestRate()
                , request.getDuration(), request.getStartDate()))
                .willReturn(somePlanItems);

        MvcResult mvcResult = mockMvc.perform(post(GENERATE_PLAN_URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        verify(planGeneratorService, times(1)).generatePaybackPlan(request.getLoanAmount()
                , request.getInterestRate(), request.getDuration(), request.getStartDate());

        List<PlanResponseItem> actualPlanItems = parseResponseToListOfResponseItems(mvcResult.getResponse());

        assertThat(actualPlanItems).hasSize(somePlanItems.size());
    }

    private List<PlanItem> generateSomePlanItems(int duration) {
        return IntStream.range(0, duration)
                .mapToObj(i -> PlanItemTestDataBuilder.aValidPlanItem().build())
                .collect(Collectors.toList());
    }

    private List<PlanResponseItem> parseResponseToListOfResponseItems(MockHttpServletResponse response) throws IOException {
        String actualResponseBody = response.getContentAsString();
        return objectMapper.readValue(actualResponseBody, new TypeReference<List<PlanResponseItem>>() {
        });
    }

    @Test
    void shouldGetBadRequestWhenInterestRateIsNegative() throws Exception {
        PlanRequest request = PlanRequestTestDataBuilder.aValidRequest()
                .but()
                .withNegativeInterestRate()
                .build();

        mockMvc.perform(post(GENERATE_PLAN_URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetBadRequestWhenStartDateIsNull() throws Exception {
        PlanRequest request = PlanRequestTestDataBuilder.aValidRequest()
                .but()
                .withStartDate(null)
                .build();

        mockMvc.perform(post(GENERATE_PLAN_URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}