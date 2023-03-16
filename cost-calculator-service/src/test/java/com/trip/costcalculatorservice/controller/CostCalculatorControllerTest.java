package com.trip.costcalculatorservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static com.trip.costcalculatorservice.Constants.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest
class CostCalculatorControllerTest {

    //Injecting the bean of WebClient.Builder
    @Autowired
    private WebClient.Builder webClientBuilder;

    CostCalculatorController costCalculatorController = new CostCalculatorController();

    //Going eastbound from "Highway 401" to "Markham Road".
    //Should give the cost of $14.59.
    @Test
    void shouldGiveCostFromHighway401ToMavisRoad() {
        Double distance = webClientBuilder.
                baseUrl(BASE_URL_DISTANCE).
                build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(PATH_DISTANCE)
                        .queryParam(PARAM_START_LOCATION, "Highway 401")
                        .queryParam(PARAM_END_LOCATION, "Markham Road")
                        .build()
                )
                .retrieve()
                .bodyToMono(Double.class)
                .block();

        Double cost = costCalculatorController.getCost(distance);

        Assertions.assertEquals(14.59, cost);
    }

    //Going westbound from "Markham Road" to "Highway 401".
    //Should give the cost of $14.59.
    @Test
    void shouldGiveCostFromMavisRoadToHighway401() {
        Double distance = webClientBuilder.
                baseUrl(BASE_URL_DISTANCE).
                build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(PATH_DISTANCE)
                        .queryParam(PARAM_START_LOCATION, "Markham Road")
                        .queryParam(PARAM_END_LOCATION, "Highway 401")
                        .build()
                )
                .retrieve()
                .bodyToMono(Double.class)
                .block();

        Double cost = costCalculatorController.getCost(distance);

        Assertions.assertEquals(14.59, cost);
    }

    //If the start and end location is same, then should give the cost of $0.0.
    @Test
    void shouldGiveCostZeroWhenStartAndEndLocationIsSame() {
        Double distance = webClientBuilder.
                baseUrl(BASE_URL_DISTANCE).
                build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(PATH_DISTANCE)
                        .queryParam(PARAM_START_LOCATION, "Markham Road")
                        .queryParam(PARAM_END_LOCATION, "Markham Road")
                        .build()
                )
                .retrieve()
                .bodyToMono(Double.class)
                .block();

        Double cost = costCalculatorController.getCost(distance);

        Assertions.assertEquals(0, cost);
    }
}