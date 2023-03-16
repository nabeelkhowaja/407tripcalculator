package com.trip.distancecalculatorservice.controller;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static com.trip.distancecalculatorservice.Constants.TWO_DIGIT_DECIMAL;
import static org.junit.jupiter.api.Assertions.*;

class DistanceCalculatorControllerTest {

    DecimalFormat df = new DecimalFormat(TWO_DIGIT_DECIMAL);

    //Going eastbound from "Highway 401" to "Markham Road"
    //Should give the distance of 58.36 km.
    @Test
    void shouldCalculateDistanceFromHighway401ToMavisRoad() {
        DistanceCalculatorController distanceCalculatorController = new DistanceCalculatorController();
        Double distance = distanceCalculatorController.getDistance("Highway 401", "Markham Road");
        Double distanceRoundToTwoDecimalPlaces = Double.valueOf(df.format(distance));
        assertEquals(58.36, distanceRoundToTwoDecimalPlaces);
    }

    //Going westbound from "Markham Road" to "Highway 401"
    //Should also give the distance of 58.36 km.
    @Test
    void shouldCalculateDistanceFromMavisRoadToHighway401() {
        DistanceCalculatorController distanceCalculatorController = new DistanceCalculatorController();
        Double distance = distanceCalculatorController.getDistance("Markham Road", "Highway 401");
        Double distanceRoundToTwoDecimalPlaces = Double.valueOf(df.format(distance));
        assertEquals(58.36, distanceRoundToTwoDecimalPlaces);
    }

    //If the start and end location is same, then should give the distance of 0.0km.
    @Test
    void shouldGiveDistanceZeroWhenStartAndEndLocationIsSame() {
        DistanceCalculatorController distanceCalculatorController = new DistanceCalculatorController();
        Double distance = distanceCalculatorController.getDistance("Markham Road", "Markham Road");
        Double distanceRoundToTwoDecimalPlaces = Double.valueOf(df.format(distance));
        assertEquals(0.0, distanceRoundToTwoDecimalPlaces);
    }
}