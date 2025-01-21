package ca.uqam.example;

import ca.uqam.example.adapter.flight.FlightCatalog;
import ca.uqam.example.adapter.flight.impl.FlightCatalogImpl;
import ca.uqam.example.adapter.flight.impl.FlightSeatsCounter;
import ca.uqam.example.adapter.plane.PlaneCatalog;
import ca.uqam.example.adapter.plane.impl.PlaneCatalogImpl;
import ca.uqam.example.model.flight.FlightInformation;
import ca.uqam.example.model.passenger.*;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        FlightCatalog flightCatalog = new FlightCatalogImpl();
        PlaneCatalog planeCatalog = new PlaneCatalogImpl();
        System.out.println("ServiceStarted...");
        Scanner scanner = new Scanner(System.in);
        boolean shouldContinue = true;
        while (shouldContinue) {
            System.out.println("Enter Passenger passport: ");
            String passengerPassport = scanner.nextLine();
            System.out.println("Enter Passenger name: ");
            String passengerName = scanner.nextLine();
            System.out.println("Enter Passenger age: ");
            int passengerAge = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter flight number: ");
            String flightInput = scanner.nextLine();
            FlightInformation flight = flightCatalog.getFlightInformation(flightInput);
            if (flight == null) {
                System.out.println("No flight found with this code, try again...");
                continue;
            }
            FlightSeatsCounter flightSeatsCounter = new FlightSeatsCounter(planeCatalog, flightCatalog, flightInput);
            System.out.println("Enter a passenger type: write first for First Class, " +
                    "business for Business Class or " +
                    "economy for Economy Class");
            String passengerClass = scanner.nextLine();
            PassengerType passengerType = PassengerType.ECONOMY_CLASS;
            boolean passengerFound = false;
            while (!passengerFound) {
                switch (passengerClass) {
                    case "first" -> {
                        passengerType = PassengerType.FIRST_CLASS;
                        passengerFound = true;
                    }
                    case "business" -> {
                        passengerType = PassengerType.BUSINESS_CLASS;
                        passengerFound = true;
                    }
                    case "economy" -> {
                        passengerType = PassengerType.ECONOMY_CLASS;
                        passengerFound = true;
                    }
                    default -> {
                        System.out.println("Passenger type not existent, please try again");
                        passengerFound = false;
                    }
                }
            }
            Passenger passenger = null;
            if (flightSeatsCounter.numberOfTotalSeatsAvailable() != 0) {
                if (passengerType.equals(PassengerType.FIRST_CLASS)) {
                    passenger = new FirstClassPassenger(passengerPassport, passengerName, passengerAge);
                    if (flightSeatsCounter.numberOfFirstClassSeatsAvailable() == 0) {
                        System.out.println("First Class is Full. Trying Business...");
                        passenger = new BusinessClassPassenger(passengerPassport, passengerName, passengerAge);
                    }
                }
                if (passengerType.equals(PassengerType.BUSINESS_CLASS)) {
                    if (flightSeatsCounter.numberOfBusinessClassSeatsAvailable() == 0) {
                        System.out.println("Business Class is Full. Trying Economy...");
                        passenger = new EconomiClassPassenger(passengerPassport, passengerName, passengerAge);
                    }
                }
                if (passengerType.equals(PassengerType.ECONOMY_CLASS)) {
                    passenger = new EconomiClassPassenger(passengerPassport, passengerName, passengerAge);
                    if (flightSeatsCounter.numberOfEconomyClassSeatsAvailable() == 0) {
                        System.out.println("Flight is Full. Try another flight...");
                        scanner.close();
                        break;
                    }
                }
                assert passenger != null;
                flightSeatsCounter.addPassenger(passenger);
            } else {
                System.out.println("Flight is Full. Try another flight...");
                scanner.close();
                break;
            }
            System.out.println("Seats available: " + flightSeatsCounter.numberOfTotalSeatsAvailable());
            System.out.println("Continue adding passengers? yes or no?");
            String continueChoice = scanner.nextLine();
            if ("no".equalsIgnoreCase(continueChoice)) {
                shouldContinue = false;
            }
        }
    }
}
