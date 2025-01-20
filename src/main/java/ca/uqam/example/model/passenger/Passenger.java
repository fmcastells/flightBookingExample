package ca.uqam.example.model.passenger;

public abstract class Passenger {

    private String passport;
    private String name;
    private double age;
    private PassengerType type;

    public Passenger(String passport, String name, double age) {
        this.passport = passport;
        this.name = name;
        this.age = age;
    }

    public abstract PassengerType getType();

    public String getPassport() {
        return passport;
    }

    public String getName() {
        return name;
    }

    public double getAge() {
        return age;
    }
}
