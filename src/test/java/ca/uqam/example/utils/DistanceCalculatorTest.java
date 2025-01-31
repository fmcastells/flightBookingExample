package ca.uqam.example.utils;

import ca.uqam.example.adapter.flight.FlightCatalog;
import ca.uqam.example.model.flight.FlightInformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class DistanceCalculatorTest {

    private static String FLIGHT_NUMBER = "flightNumber";
    @InjectMocks
    private DistanceCalculator distanceCalculator;
    @Mock
    private FlightCatalog flightCatalog;
    @Mock
    private FlightInformation flightInformation;

    @Test
    void getDistanceReturnRealDistance() {
        // Given
        given(flightCatalog.getFlightInformation(FLIGHT_NUMBER)).willReturn(flightInformation);
        given(flightInformation.getLatSource()).willReturn(123.00);
        given(flightInformation.getLonSource()).willReturn(123.00);
        given(flightInformation.getLatDestination()).willReturn(123.00);
        given(flightInformation.getLonDestination()).willReturn(123.00);

        // When
        double actualDistance = distanceCalculator.getDistance(FLIGHT_NUMBER);

        // Then
        assertThat(actualDistance).isEqualTo(0.0);
    }

}
