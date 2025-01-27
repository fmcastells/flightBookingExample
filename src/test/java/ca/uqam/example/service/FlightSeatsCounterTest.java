package ca.uqam.example.service;

import ca.uqam.example.adapter.flight.FlightCatalog;
import ca.uqam.example.adapter.plane.PlaneCatalog;
import ca.uqam.example.model.flight.FlightInformation;
import ca.uqam.example.model.passenger.Passenger;
import ca.uqam.example.model.passenger.PassengerType;
import ca.uqam.example.model.plane.PlaneType;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FlightSeatsCounterTest {

    private FlightSeatsCounter flightSeatsCounter;
    private PlaneCatalog planeCatalog;
    private FlightCatalog flightCatalog;
    private FlightInformation flightInformation;
    private String flightNumber;
    private Passenger passenger;
    private PlaneType planeType;

    @BeforeEach
    void setup() {
        flightNumber = "flightNumber";
        planeType = PlaneType.BOEING;
        planeCatalog = mock(PlaneCatalog.class);
        flightCatalog = mock(FlightCatalog.class);
        flightInformation = mock(FlightInformation.class);
        passenger = mock(Passenger.class);

        given(flightInformation.getPlaneType()).willReturn(planeType);
        given(flightCatalog.getFlightInformation(flightNumber)).willReturn(flightInformation);
        given(planeCatalog.getNumberSeatsFirstClass(planeType)).willReturn(1);
        given(planeCatalog.getNumberSeatsBusinessClass(planeType)).willReturn(1);
        given(planeCatalog.getNumberSeatsEconomyClass(planeType)).willReturn(1);

        flightSeatsCounter = new FlightSeatsCounter(planeCatalog, flightCatalog, flightNumber);
    }

    @Test
    void addFirstClassPassenger() {
        // Given
        given(passenger.getType()).willReturn(PassengerType.FIRST_CLASS);

        // When
        flightSeatsCounter.addPassenger(passenger);
        int numberOfFirstClassSeatsAvailable = flightSeatsCounter.numberOfFirstClassSeatsAvailable();

        // Then
        assertThat(numberOfFirstClassSeatsAvailable).isEqualTo(0);
    }

    @Test
    void addBusinessClassPassenger() {
        // Given
        given(passenger.getType()).willReturn(PassengerType.BUSINESS_CLASS);

        // When
        flightSeatsCounter.addPassenger(passenger);
        int numberOfBusinessClassSeatsAvailable = flightSeatsCounter.numberOfBusinessClassSeatsAvailable();

        // Then
        assertThat(numberOfBusinessClassSeatsAvailable).isEqualTo(0);
    }

    @Test
    void addEconomyClassPassenger() {
        // Given
        given(passenger.getType()).willReturn(PassengerType.ECONOMY_CLASS);

        // When
        flightSeatsCounter.addPassenger(passenger);
        int numberOfEconomyClassSeatsAvailable = flightSeatsCounter.numberOfEconomyClassSeatsAvailable();

        // Then
        assertThat(numberOfEconomyClassSeatsAvailable).isEqualTo(0);
    }

    @Test
    void numberOfTotalSeatsAvailable() {
        // Given
        given(passenger.getType()).willReturn(PassengerType.ECONOMY_CLASS);

        // When
        flightSeatsCounter.addPassenger(passenger);
        int numberOfTotalSeatsAvailable = flightSeatsCounter.numberOfTotalSeatsAvailable();

        // Then
        assertThat(numberOfTotalSeatsAvailable).isEqualTo(2);
    }

}