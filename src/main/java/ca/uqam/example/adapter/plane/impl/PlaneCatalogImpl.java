package ca.uqam.example.adapter.plane.impl;

import ca.uqam.example.adapter.plane.PlaneCatalog;
import ca.uqam.example.model.plane.PlaneInformation;
import ca.uqam.example.model.plane.PlaneType;

import java.util.HashMap;
import java.util.Map;

public class PlaneCatalogImpl implements PlaneCatalog {

    private Map<PlaneType, PlaneInformation> planeMap = new HashMap<>();

    public PlaneCatalogImpl() {
        planeMap.put(PlaneType.BOEING, new PlaneInformation(PlaneType.BOEING, 20, 25, 120));
        planeMap.put(PlaneType.AIRBUS, new PlaneInformation(PlaneType.AIRBUS, 13, 18, 90));
        planeMap.put(PlaneType.EMBRAER, new PlaneInformation(PlaneType.EMBRAER, 5, 10, 50));
        planeMap.put(PlaneType.BOMBARDIER, new PlaneInformation(PlaneType.BOMBARDIER, 8, 13, 75));
    }

    @Override
    public int getNumberSeatsFirstClass(PlaneType planeType) {
        return planeMap.get(planeType).getNumberSeatsFirstClass();
    }

    @Override
    public int getNumberSeatsBusinessClass(PlaneType planeType) {
        return planeMap.get(planeType).getNumberSeatsBusinessClass();
    }

    @Override
    public int getNumberSeatsEconomyClass(PlaneType planeType) {
        return planeMap.get(planeType).getNumberSeatsEconomyClass();
    }

    @Override
    public int getNumberOfTotalSeats(PlaneType planeType) {
        return planeMap.get(planeType).getNumberSeatsFirstClass() +
                planeMap.get(planeType).getNumberSeatsBusinessClass() +
                planeMap.get(planeType).getNumberSeatsEconomyClass();
    }
}
