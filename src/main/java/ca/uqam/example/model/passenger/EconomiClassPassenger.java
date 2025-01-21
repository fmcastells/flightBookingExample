package ca.uqam.example.model.passenger;

public class EconomiClassPassenger extends Passenger {

    public EconomiClassPassenger(String passport, String name, int age) {
        super(passport, name, age);
    }

    @Override
    public PassengerType getType() {
        return PassengerType.ECONOMY_CLASS;
    }
}
