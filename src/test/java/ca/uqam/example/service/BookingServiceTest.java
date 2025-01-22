package ca.uqam.example.service;

import ca.uqam.example.model.passenger.EconomiClassPassenger;
import ca.uqam.example.model.passenger.FirstClassPassenger;
import ca.uqam.example.model.passenger.Passenger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    private FlightSeatsCounter flightSeatsCounter;
    private BookingService bookingService;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Passenger passenger;

    @BeforeEach
    void setup() {
        flightSeatsCounter = mock(FlightSeatsCounter.class);
        bookingService = new BookingService(flightSeatsCounter);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void successfullyAddPassenger() {
        // Given
        given(flightSeatsCounter.numberOfTotalSeatsAvailable()).willReturn(1);
        passenger = mock(EconomiClassPassenger.class);
        given(flightSeatsCounter.numberOfEconomyClassSeatsAvailable()).willReturn(1);

        // When
        bookingService.book(passenger);

        // Then
        verify(flightSeatsCounter).numberOfEconomyClassSeatsAvailable();
        verify(flightSeatsCounter).addPassenger(passenger);
        assertThat("Passenger added successfully").isEqualTo(outputStreamCaptor.toString().trim());
    }

    @Test
    void testNumberOfTotalSeatsEqualZero() {
        // Given
        given(flightSeatsCounter.numberOfTotalSeatsAvailable()).willReturn(0);
        passenger = mock(Passenger.class);

        // When
        bookingService.book(passenger);

        // Then
        verify(flightSeatsCounter).numberOfTotalSeatsAvailable();
        verify(flightSeatsCounter, never()).addPassenger(passenger);
        assertThat("Flight is Full...").isEqualTo(outputStreamCaptor.toString().trim());
    }

    @Test
    void testPassingToAllTypesOfPassengers() {
        // Given
        given(flightSeatsCounter.numberOfTotalSeatsAvailable()).willReturn(1);
        passenger = mock(FirstClassPassenger.class);
        given(flightSeatsCounter.numberOfFirstClassSeatsAvailable()).willReturn(0);
        given(flightSeatsCounter.numberOfBusinessClassSeatsAvailable()).willReturn(0);
        given(flightSeatsCounter.numberOfEconomyClassSeatsAvailable()).willReturn(0);

        // When
        bookingService.book(passenger);

        // Then
        verify(flightSeatsCounter).numberOfTotalSeatsAvailable();
        verify(flightSeatsCounter).numberOfFirstClassSeatsAvailable();
        verify(flightSeatsCounter, never()).addPassenger(passenger);
        assertThat("First Class is Full. Trying Business...\n" +
                "Business Class is Full. Trying Economy...\n" +
                "Flight is Full. Try another flight...").isEqualTo(outputStreamCaptor.toString().trim());
    }
}