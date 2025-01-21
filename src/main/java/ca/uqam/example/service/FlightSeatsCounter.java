package ca.uqam.example.service;

import ca.uqam.example.adapter.flight.FlightCatalog;
import ca.uqam.example.adapter.plane.PlaneCatalog;
import ca.uqam.example.model.passenger.Passenger;

public class FlightSeatsCounter {

    private int totalAmountFirstClassSeats;
    private int totalAmountBusinessClassSeats;
    private int totalAmountEconomyClassSeats;

    public FlightSeatsCounter(PlaneCatalog planeCatalog, FlightCatalog flightCatalog, String flightNumber) {
        this.totalAmountFirstClassSeats = planeCatalog.getNumberSeatsFirstClass(
                flightCatalog.getFlightInformation(flightNumber).getPlaneType());
        this.totalAmountBusinessClassSeats = planeCatalog.getNumberSeatsBusinessClass(
                flightCatalog.getFlightInformation(flightNumber).getPlaneType());
        this.totalAmountEconomyClassSeats = planeCatalog.getNumberSeatsEconomyClass(
                flightCatalog.getFlightInformation(flightNumber).getPlaneType());
    }

    public void addPassenger(Passenger passenger) {
        switch (passenger.getType()) {
            case FIRST_CLASS -> {
                if (totalAmountFirstClassSeats != 0) {
                    totalAmountFirstClassSeats = totalAmountFirstClassSeats - 1;
                }
            }
            case BUSINESS_CLASS -> {
                if (totalAmountBusinessClassSeats != 0) {
                    totalAmountBusinessClassSeats = totalAmountBusinessClassSeats - 1;
                }
            }
            case ECONOMY_CLASS -> {
                if (totalAmountEconomyClassSeats != 0) {
                    totalAmountEconomyClassSeats = totalAmountEconomyClassSeats - 1;
                }
            }
            default -> System.out.println("Passenger Type not specified");
        }
    }

    public int numberOfFirstClassSeatsAvailable() {
        return totalAmountFirstClassSeats;
    }

    public int numberOfBusinessClassSeatsAvailable() {
        return totalAmountBusinessClassSeats;
    }

    public int numberOfEconomyClassSeatsAvailable() {
        return totalAmountEconomyClassSeats;
    }

    public int numberOfTotalSeatsAvailable() {
        return totalAmountFirstClassSeats + totalAmountBusinessClassSeats + totalAmountEconomyClassSeats;
    }

}
