package ca.uqam.example.model.passenger;

public class FirstClassPassenger extends Passenger {

    public FirstClassPassenger(String passport, String name, int age) {
        super(passport, name, age);
    }

    @Override
    public PassengerType getType() {
        return PassengerType.FIRST_CLASS;
    }
}
