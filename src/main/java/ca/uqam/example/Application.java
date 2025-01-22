package ca.uqam.example;

import ca.uqam.example.adapter.flight.FlightCatalog;
import ca.uqam.example.adapter.flight.impl.FlightCatalogImpl;
import ca.uqam.example.service.FlightSeatsCounter;
import ca.uqam.example.adapter.plane.PlaneCatalog;
import ca.uqam.example.adapter.plane.impl.PlaneCatalogImpl;
import ca.uqam.example.model.passenger.Passenger;
import ca.uqam.example.service.BookingService;
import ca.uqam.example.service.FlightService;
import ca.uqam.example.service.PassengerService;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        System.out.println("ServiceStarted...");
        Scanner scanner = new Scanner(System.in);
        PlaneCatalog planeCatalog = new PlaneCatalogImpl();
        FlightCatalog flightCatalog = new FlightCatalogImpl();
        FlightService flightService = new FlightService(flightCatalog);
        String flightNumber = flightService.getFlightInformation(scanner).getFlightNumber();
        FlightSeatsCounter flightSeatsCounter = new FlightSeatsCounter(planeCatalog, flightCatalog, flightNumber);
        boolean shouldContinue = true;
        while (shouldContinue) {
            PassengerService passengerService = new PassengerService();
            Passenger passenger = passengerService.createPassenger(scanner);
            BookingService bookingService = new BookingService(flightSeatsCounter);
            bookingService.book(passenger);
            System.out.println("Seats available: " + flightSeatsCounter.numberOfTotalSeatsAvailable());
            System.out.println("Continue adding passengers? yes or no?");
            String continueChoice = scanner.nextLine();
            if ("no".equalsIgnoreCase(continueChoice)) {
                shouldContinue = false;
            }
        }
    }
}
