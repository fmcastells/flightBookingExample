package ca.uqam.example.utils;

import ca.uqam.example.adapter.flight.FlightCatalog;
import ca.uqam.example.model.flight.FlightInformation;

public class DistanceCalculator {
    private static final int EARTH_RADIUS = 6371;

    private FlightCatalog flightCatalog;

    public double getDistance(String flightNumber) {
        FlightInformation flightInformation = flightCatalog.getFlightInformation(flightNumber);
        double latSource = flightInformation.getLatSource();
        double lat2 = flightInformation.getLatDestination();
        double lon1 = flightInformation.getLonSource();
        double lon2 = flightInformation.getLonDestination();
        double dLat = Math.toRadians(lat2 - latSource);
        double dLon = Math.toRadians(lon2 - lon1);

        latSource = Math.toRadians(latSource);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(latSource) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));

        return EARTH_RADIUS * c;
    }
}
