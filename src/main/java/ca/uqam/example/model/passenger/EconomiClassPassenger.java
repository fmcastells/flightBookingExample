package ca.uqam.example.model.passenger;

public class EconomiClassPassenger extends Passenger {

    public EconomiClassPassenger(String passport, String name, double age) {
        super(passport, name, age);
    }

    @Override
    public PassengerType getType() {
        return PassengerType.ECONOMIC_CLASS;
    }
}
