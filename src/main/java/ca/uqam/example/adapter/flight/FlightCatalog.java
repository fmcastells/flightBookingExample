package ca.uqam.example.adapter.flight;

import ca.uqam.example.model.flight.FlightInformation;

public interface FlightCatalog {

    FlightInformation getFlightInformation(String flightNumber);
}
