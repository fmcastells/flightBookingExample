package ca.uqam.example.service;

import ca.uqam.example.adapter.flight.FlightCatalog;
import ca.uqam.example.adapter.plane.PlaneCatalog;

public class FlightStatus {

    private FlightCatalog flightCatalog;
    private PlaneCatalog planeCatalog;

    public FlightStatus(FlightCatalog flightCatalog, PlaneCatalog planeCatalog) {
        this.flightCatalog = flightCatalog;
        this.planeCatalog = planeCatalog;
    }

    public FlightStatus getFlightPassengerStatus(String flightNumber) {
        flightCatalog.getFlightInformation(flightNumber);
        planeCatalog.getNumberOfTotalSeats(flightCatalog.getFlightInformation(flightNumber).getPlaneType());
    }

    public void addPassenger() {

    }
}
