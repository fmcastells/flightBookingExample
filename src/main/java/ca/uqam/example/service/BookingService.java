package ca.uqam.example.service;

import ca.uqam.example.adapter.flight.impl.FlightSeatsCounter;
import ca.uqam.example.model.passenger.BusinessClassPassenger;
import ca.uqam.example.model.passenger.EconomiClassPassenger;
import ca.uqam.example.model.passenger.FirstClassPassenger;
import ca.uqam.example.model.passenger.Passenger;

public class BookingService {

    private FlightSeatsCounter flightSeatsCounter;

    public BookingService(FlightSeatsCounter flightSeatsCounter) {
        this.flightSeatsCounter = flightSeatsCounter;
    }

    public void book(Passenger passenger, String flightNumber) {
        if (flightSeatsCounter.numberOfTotalSeatsAvailable() != 0) {
            if (passenger instanceof FirstClassPassenger) {
                if (flightSeatsCounter.numberOfFirstClassSeatsAvailable() == 0) {
                    System.out.println("First Class is Full. Trying Business...");
                    passenger = new BusinessClassPassenger(passenger.getPassport(), passenger.getName(), passenger.getAge());
                }
            }
            if (passenger instanceof BusinessClassPassenger) {
                if (flightSeatsCounter.numberOfBusinessClassSeatsAvailable() == 0) {
                    System.out.println("Business Class is Full. Trying Economy...");
                    passenger = new EconomiClassPassenger(passenger.getPassport(), passenger.getName(), passenger.getAge());
                }
            }
            if (passenger instanceof EconomiClassPassenger) {
                if (flightSeatsCounter.numberOfEconomyClassSeatsAvailable() == 0) {
                    System.out.println("Flight is Full. Try another flight...");
                }
            }
            flightSeatsCounter.addPassenger(passenger);
        } else {
            System.out.println("Flight is Full...");
        }
    }
}
