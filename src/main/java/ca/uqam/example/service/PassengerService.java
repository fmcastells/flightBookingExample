package ca.uqam.example.service;

import ca.uqam.example.model.passenger.*;

import java.util.Scanner;

public class PassengerService {

    public Passenger createPassenger(Scanner scanner) {
        System.out.println("Enter Passenger passport: ");
        String passengerPassport = scanner.nextLine();
        System.out.println("Enter Passenger name: ");
        String passengerName = scanner.nextLine();
        System.out.println("Enter Passenger age: ");
        int passengerAge = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter a passenger type: write first for First Class, " +
                "business for Business Class or " +
                "economy for Economy Class");
        String passengerClass = scanner.nextLine();
        Passenger passenger = null;
        boolean passengerFound = false;
        while (!passengerFound) {
            switch (passengerClass) {
                case "first" -> {
                    passenger = new FirstClassPassenger(passengerPassport, passengerName, passengerAge);
                    passengerFound = true;
                }
                case "business" -> {
                    passenger = new BusinessClassPassenger(passengerPassport, passengerName, passengerAge);
                    passengerFound = true;
                }
                case "economy" -> {
                    passenger = new EconomiClassPassenger(passengerPassport, passengerName, passengerAge);
                    passengerFound = true;
                }
                default -> {
                    System.out.println("Passenger type not existent, please try again");
                    passengerFound = false;
                    return null;
                }
            }
        }
        return passenger;
    }
}
