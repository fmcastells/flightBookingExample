package ca.uqam.example.adapter.plane;

import ca.uqam.example.model.plane.PlaneType;

public interface PlaneCatalog {

    int getNumberSeatsFirstClass(PlaneType planeType);
    int getNumberSeatsBusinessClass(PlaneType planeType);
    int getNumberSeatsEconomyClass(PlaneType planeType);

    int getNumberOfTotalSeats(PlaneType planeType);
}
