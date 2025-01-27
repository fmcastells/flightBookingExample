package ca.uqam.example.adapter.plane.impl;

import ca.uqam.example.model.plane.PlaneType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PlaneCatalogImplTest {

    @ParameterizedTest
    @CsvSource({"BOEING,2,5,12,19", "AIRBUS,1,1,9,11", "EMBRAER,0,1,4,5", "BOMBARDIER,0,2,5,7"})
    void getNumberOfTotalSeats(PlaneType planeType, int numberOfFirstClass, int numberOfBusinessClass, int numberOfEconomyClass, int totalNumberOfSeats) {
        // Given
        PlaneCatalogImpl planeCatalog = new PlaneCatalogImpl();

        // WHen
        int numberSeatsFirstClass = planeCatalog.getNumberSeatsFirstClass(planeType);
        int numberSeatsBusinessClass = planeCatalog.getNumberSeatsBusinessClass(planeType);
        int numberSeatsEconomyClass = planeCatalog.getNumberSeatsEconomyClass(planeType);
        int numberOfTotalSeats = planeCatalog.getNumberOfTotalSeats(planeType);

        // Then
        assertThat(numberOfFirstClass). isEqualTo(numberSeatsFirstClass);
        assertThat(numberSeatsBusinessClass). isEqualTo(numberOfBusinessClass);
        assertThat(numberSeatsEconomyClass). isEqualTo(numberOfEconomyClass);
        assertThat(numberOfTotalSeats). isEqualTo(totalNumberOfSeats);
    }
}