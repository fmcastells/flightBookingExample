package ca.uqam.example.model.passenger;

public class BusinessClassPassenger extends Passenger {

    public BusinessClassPassenger(String passport, String name, int age) {
        super(passport, name, age);
    }

    @Override
    public PassengerType getType() {
        return PassengerType.BUSINESS_CLASS;
    }
}
