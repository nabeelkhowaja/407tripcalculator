package com.trip.costcalculatorservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.DecimalFormat;

import static com.trip.costcalculatorservice.Constants.*;

@RestController
public class CostCalculatorController {

    //Injecting the bean of WebClient.Builder
    @Autowired
    private WebClient.Builder webClientBuilder;

    DecimalFormat df = new DecimalFormat(TWO_DIGIT_DECIMAL);

    ////GET request which accepts name of the start and end location as parameters
    @GetMapping("/")
    public String costOfTrip(@RequestParam String startLocation, @RequestParam String endLocation) {

        //Using the webClientBuilder, calling the distance calculator microservice
        //Giving the base URL with the path and supplying in the start and end location names.
        //Returns the distance in Double type.
        Double distance = webClientBuilder.
                baseUrl(BASE_URL_DISTANCE)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(PATH_DISTANCE)
                        .queryParam(PARAM_START_LOCATION, startLocation)
                        .queryParam(PARAM_END_LOCATION, endLocation)
                        .build()
                )
                .retrieve()
                .bodyToMono(Double.class)
                .block();

        double distanceFormatted = Double.parseDouble(df.format(distance));
        double costFormatted = getCost(distance);

        //If start or end location name is not in the JSON file, then return "Entered location(s) not found." message.
        if (distanceFormatted < 0)
            return "Entered location(s) not found.";

        return "Distance: " + distanceFormatted + "km" +
                "<br>" +
                "Cost: $" + costFormatted;
    }

    @GetMapping("/cost")
    public Double getCost(@RequestParam Double distance) {
        //Calculating the cost by multiplying distance and toll rate.
        return Double.parseDouble(df.format(distance * TOLL_RATE));
    }
}