package com.trip.distancecalculatorservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static com.trip.distancecalculatorservice.Constants.*;

@RestController
public class DistanceCalculatorController {

    //Total distance from start location to end location
    Double totalDistance;

    //ID of start and end location
    Integer startLocationId;
    Integer endLocationId;

    //Map where key is the locationID of type String, and value has the details of the locationID of type Object
    Map<String, Object> locationInfo;

    //GET request with the path 'distance'. It accepts name of the start and end location as parameters
    @GetMapping("/distance")
    public Double getDistance(@RequestParam String startLocation, @RequestParam String endLocation) {

        //Getting the JSON file resource with the file name as parameter of ClassPathResource constructor
        ClassPathResource interchangesDataResource = new ClassPathResource(JSON_FILE_NAME);

        //Setting or resetting global variables every time a request is made
        totalDistance = 0.0;
        startLocationId = null;
        endLocationId = null;

        try {
            //Getting data from JSON file and reading all the bytes in the input stream and then saving the data as String
            String interchangesDataString = new String(interchangesDataResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            //Saving all the JSON objects of key "locations" as Map<String, Object>,
            //where String is the locationID , and Object contains the details of the locationID.
            Object allLocations = new ObjectMapper().readValue(interchangesDataString, Map.class).get(LOCATIONS);
            locationInfo = new ObjectMapper().convertValue(allLocations, Map.class);

            //Iterating each location to get the IDs of start and end location by their name.
            //Once the IDs are found, calling calculateDistance method with the argument startLocationID, as the
            //distance will be calculated from the start location.
            for (Map.Entry<String, Object> entry : locationInfo.entrySet()) {
                int locationId = Integer.parseInt(entry.getKey());
                String name = ((LinkedHashMap<?, ?>) entry.getValue()).get(NAME).toString();

                if (name.equals(startLocation))
                    startLocationId = locationId;

                if (name.equals(endLocation))
                    endLocationId = locationId;

                if (startLocationId != null && endLocationId != null) {
                    DecimalFormat df = new DecimalFormat(TWO_DIGIT_DECIMAL);
                    return Double.valueOf(df.format(calculateDistance((startLocationId))));
                }
            }

            //If any of the start or end location is not found, it means that the supplied location name is not in the JSON file.
            //In this case -1.0 is returned.
            if (startLocationId == null || endLocationId == null)
                return -1.0;

        } catch (IOException e) {//Catching the exception if getting the JSON file resource causes any issue.
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return totalDistance;
    }

    public Double calculateDistance(Integer locationId) {

        Map.Entry<String, Object> currentLocation = null;

        //Getting the object of location details with supplied locationId
        for (Map.Entry<String, Object> entry : locationInfo.entrySet()) {
            String id = entry.getKey();
            if (locationId != null && Integer.parseInt(id) == locationId) {
                currentLocation = entry;
                break;
            }
        }

        if (currentLocation != null) {

            //Getting the routes of the location and saving as an Array list.
            ArrayList<?> routes = (ArrayList<?>) ((LinkedHashMap<?, ?>) currentLocation.getValue()).get(ROUTES);

            //As the direction of route can only be either eastbound or westbound. It is safe to assume that there can only be two possibilities.

            //As per the JSON,

            //If the start location ID is less than the end location ID, which means we are going eastbound.
            //In this case, the route is selected that has the toId which is greater than the ID of current location.

            //Similarly,

            //If the start location ID is greater than the end location ID, which means we are going westbound.
            //In this case, the route is selected that has the toId which is less than the ID of current location.


            Integer nextId = null;
            for (Object route : routes) {
                Integer toId = (Integer) (((LinkedHashMap<?, ?>) route).get(TO_ID));
                if (((startLocationId < endLocationId) && (Integer.parseInt(currentLocation.getKey()) < toId))
                        || ((startLocationId > endLocationId) && (Integer.parseInt(currentLocation.getKey()) > toId))
                ) {
                    //Adding distance of the selected route to the totalDistance
                    totalDistance += (Double) (((LinkedHashMap<?, ?>) route).get(DISTANCE));
                    nextId = toId;
                    break;
                }
            }

            //Traversing only if the end location hasn't arrived yet.
            if (!Objects.equals(nextId, endLocationId))
                calculateDistance(nextId);

        }

        return totalDistance;
    }
}