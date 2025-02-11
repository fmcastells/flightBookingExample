package ca.uqam.mgl7230.tp1.config;

import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalogImpl;
import ca.uqam.mgl7230.tp1.adapter.persist.SavePassengerInFlight;
import ca.uqam.mgl7230.tp1.adapter.plane.PlaneCatalog;
import ca.uqam.mgl7230.tp1.adapter.plane.PlaneCatalogImpl;
import ca.uqam.mgl7230.tp1.service.BookingService;
import ca.uqam.mgl7230.tp1.service.FlightPassengerService;
import ca.uqam.mgl7230.tp1.service.PassengerService;
import ca.uqam.mgl7230.tp1.service.prompt.FlightPromptService;
import ca.uqam.mgl7230.tp1.service.prompt.PassengerPromptService;
import ca.uqam.mgl7230.tp1.utils.DistanceCalculator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ApplicationInitializer {

    private static FlightPassengerService flightPassengerService;
    private static FlightPromptService flightPromptService;
    private static PassengerPromptService passengerPromptService;
    private static BookingService bookingService;
    private static PassengerService passengerService;
    private static SavePassengerInFlight savePassengerInFlight;
    private static Scanner scanner;
    private static FlightCatalog flightCatalog;
    private static FileWriterProvider fileWriterProvider;

    public static void init() {
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        PlaneCatalog planeCatalog = new PlaneCatalogImpl();
        fileWriterProvider = new FileWriterProvider();
        flightCatalog = new FlightCatalogImpl();
        flightPromptService = new FlightPromptService(flightCatalog);
        scanner = new Scanner(System.in);
        savePassengerInFlight = new SavePassengerInFlight();
        passengerService = new PassengerService(distanceCalculator);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog);
        bookingService = new BookingService(flightPassengerService, passengerService);
        passengerPromptService = new PassengerPromptService();

    }

    public static FlightPassengerService flightPassengerService() {
        return flightPassengerService;
    }

    public static PassengerPromptService passengerPromptService() {
        return passengerPromptService;
    }

    public static BookingService bookingService() {
        return bookingService;
    }

    public static PassengerService passengerService() {
        return passengerService;
    }

    public static SavePassengerInFlight savePassengerInFlight() {
        return savePassengerInFlight;
    }

    public static Scanner scanner() {
        return scanner;
    }

    public static FlightCatalog flightCatalog() {
        return flightCatalog;
    }

    public static FlightPromptService flightPromptService() {
        return flightPromptService;
    }

    public static FileWriterProvider fileWriterProvider() {
        return fileWriterProvider;
    }
}
