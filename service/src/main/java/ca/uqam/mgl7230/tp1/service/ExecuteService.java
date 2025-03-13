package ca.uqam.mgl7230.tp1.service;

import ca.uqam.mgl7230.tp1.model.passenger.Passenger;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerKeyConstants;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static ca.uqam.mgl7230.tp1.config.ApplicationInitializer.*;

public class ExecuteService {

    public static void execute() {
        try {
            FileWriter fileWriter = fileWriterProvider().createFile("passengerData.csv");
            fileWriter.flush();
            String flightNumber = flightPromptService().getFlightInformation(scanner()).getFlightNumber();
            flightPassengerService().initializeFlightService(flightNumber);
            boolean shouldContinue = true;
            while (shouldContinue) {
                Map<PassengerKeyConstants, Object> passengerData =
                        passengerPromptService().getPassengerData(scanner());
                Passenger passenger = passengerService().createPassenger(
                        flightCatalog().getFlightInformation(flightNumber), passengerData);
                bookingService().book(passenger, flightCatalog().getFlightInformation(flightNumber));
                savePassengerInFlight().save(fileWriter, passenger, flightNumber);
                System.out.println("Seats available: " + flightPassengerService().numberOfTotalSeatsAvailable());
                System.out.println("Continue adding passengers to this flight? yes or no");
                String continueChoice = scanner().nextLine();
                if ("no".equalsIgnoreCase(continueChoice)) {
                    shouldContinue = false;
                    fileWriter.close();
                    scanner().close();
                }
            }
        } catch (IOException e) {
            System.out.println("Error manipulating file: " + e.getMessage());
        }
    }
}
