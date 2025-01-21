package ca.uqam.example.service;

import ca.uqam.example.adapter.flight.FlightCatalog;
import ca.uqam.example.model.flight.FlightInformation;

import java.util.Scanner;

public class FlightService {

    private FlightCatalog flightCatalog;

    public FlightService(FlightCatalog flightCatalog) {
        this.flightCatalog = flightCatalog;
    }

    public FlightInformation getFlightInformation(Scanner scanner) {
        System.out.println("Enter flight number: ");
        String flightInput = scanner.nextLine();
        FlightInformation flight = flightCatalog.getFlightInformation(flightInput);
        if (flight == null) {
            System.out.println("No flight found with this code, try again...");
        }
        return flight;
    }
}
