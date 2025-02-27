package ca.uqam.mgl7230.tp1.rest;

import ca.uqam.mgl7230.tp1.model.passenger.Passenger;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerKeyConstants;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static ca.uqam.mgl7230.tp1.config.ServerInitializer.*;
import static ca.uqam.mgl7230.tp1.service.ExecuteService.execute;

public class FlightBookingController implements HttpHandler {

    @Override
    public void handle(final HttpExchange exchange) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        // Process the request (example: echo the request body)
        String response = "Received: " + requestBody;
        InputStream inPassenger = new ByteArrayInputStream("p1\nn1\n1\nfirst\n".getBytes());
        InputStream inFlightNumber = new ByteArrayInputStream("UQAM005".getBytes());
        FileWriter fileWriter = fileWriterProvider().createFile("passengerData.csv");
        fileWriter.flush();
        String flightNumber = flightPromptService().getFlightInformation(scanner(inFlightNumber)).getFlightNumber();
        flightPassengerService().initializeFlightService(flightNumber);
        Map<PassengerKeyConstants, Object> passengerData =
                passengerPromptService().getPassengerData(scanner(inPassenger));
        Passenger passenger = passengerService().createPassenger(
                flightCatalog().getFlightInformation(flightNumber), passengerData);
        bookingService().book(passenger, flightCatalog().getFlightInformation(flightNumber));
        savePassengerInFlight().save(fileWriter, passenger, flightNumber);
        System.out.println("Seats available: " + flightPassengerService().numberOfTotalSeatsAvailable());
        System.out.println("Continue adding passengers to this flight? yes or no");
        fileWriter.close();
//        execute();

        // Set response headers and body
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
