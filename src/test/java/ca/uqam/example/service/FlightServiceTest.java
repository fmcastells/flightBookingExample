package ca.uqam.example.service;

import ca.uqam.example.adapter.flight.FlightCatalog;
import ca.uqam.example.model.flight.FlightInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    public static final String FLIGHT_NUMBER = "flightNumber";

    @InjectMocks
    private FlightService flightService;
    @Mock
    private FlightCatalog flightCatalog;
    @Mock
    private Scanner scanner;
    @Mock
    private FlightInformation expectedFlightInformation;

    @BeforeEach
    void setup() {
        given(scanner.nextLine()).willReturn(FLIGHT_NUMBER);
    }

    @Test
    void getFlightInformationReturnsFlightInformation() {
        // Given
        given(flightCatalog.getFlightInformation(FLIGHT_NUMBER))
                .willReturn(expectedFlightInformation);

        // When
        FlightInformation actualFlightInformation = flightService.getFlightInformation(scanner);

        // Then
        assertThat(actualFlightInformation).isEqualTo(expectedFlightInformation);

    }

}
