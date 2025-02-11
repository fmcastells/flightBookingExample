package ca.uqam.example.integration;

import ca.uqam.mgl7230.tp1.Application;
import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp1.adapter.plane.PlaneCatalog;
import ca.uqam.mgl7230.tp1.config.ApplicationInitializer;
import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp1.model.plane.PlaneType;
import ca.uqam.mgl7230.tp1.service.prompt.FlightPromptService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.MockedStatic;

import java.io.*;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class FlightTestDefinition {

    private String passengerPassport;
    private String passengerName;
    private int passengerAge;
    private String passengerType;
    private PassengerClass expectedPassengerType;
    private String flightNumber;
    private MockedStatic<ApplicationInitializer> mockApplicationInitializer;
    private FlightCatalog mockFlightCatalog;
    private PlaneCatalog mockPlaneCatalog;
    private FlightPromptService flightPromptService;

    @Before
    public void setup() {
        mockApplicationInitializer = mockStatic(ApplicationInitializer.class);
        mockFlightCatalog = mock(FlightCatalog.class);
        mockPlaneCatalog = mock(PlaneCatalog.class);
    }

    @Given("passenger is looking for flight number {string}")
    public void passengerIsLookingForFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Given("flight catalog is mocked")
    public void flightCatalogIsMocked() {

    }

    @Given("plane catalog is mocked where number of first class seats is {int}, number of business class seats is {int} and number of economy seats is {int}")
    public void planeCatalogIsMocked(int numberOfFirstClassSeats, int numberOfBusinessClassSeats, int numberOfEconomyClassSeats) {
        given(mockFlightCatalog.getFlightInformation(flightNumber))
                .willReturn(new FlightInformation(
                        flightNumber, 45.508888, -73.561668, -23.533773, -46.625290,
                        PlaneType.BOEING));
        flightPromptService = new FlightPromptService(mockFlightCatalog);
        mockApplicationInitializer.when(ApplicationInitializer::scanner)
                .thenReturn(new Scanner(System.in));
        mockApplicationInitializer.when(ApplicationInitializer::flightCatalog)
                .thenReturn(mockFlightCatalog);
        given(mockPlaneCatalog.getNumberSeatsFirstClass(PlaneType.BOEING))
                .willReturn(numberOfFirstClassSeats);
        given(mockPlaneCatalog.getNumberSeatsBusinessClass(PlaneType.BOEING))
                .willReturn(numberOfBusinessClassSeats);
        given(mockPlaneCatalog.getNumberSeatsEconomyClass(PlaneType.BOEING))
                .willReturn(numberOfEconomyClassSeats);
        given(mockPlaneCatalog.getNumberOfTotalSeats(PlaneType.BOEING))
                .willReturn(numberOfFirstClassSeats + numberOfBusinessClassSeats + numberOfEconomyClassSeats);
    }

    @Given("a passenger with passport number {string}")
    public void aPassengerWithPassportNumber(String passportNumber) {
        this.passengerPassport = passportNumber;
    }

    @Given("name {string}")
    public void name(String name) {
        this.passengerName = name;
    }

    @Given("age {int}")
    public void age(int age) {
        this.passengerAge = age;
    }

    @Given("looking to buy in {string} class")
    public void lookingToBuyInClass(String passengerClass) {
        this.passengerType = passengerClass;
        switch (passengerClass) {
            case "first" -> expectedPassengerType = PassengerClass.FIRST_CLASS;
            case "business" -> expectedPassengerType = PassengerClass.BUSINESS_CLASS;
            case "economy" -> expectedPassengerType = PassengerClass.ECONOMY_CLASS;
        }
    }

    @Given("passenger selects flight {string}")
    public void passengerSelectsFlight(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @When("agent pass all information to the system")
    public void agentPassAllInformationToTheSystem() {
        String information = flightNumber + "\n" +
                passengerPassport + "\n" +
                passengerName + "\n" +
                passengerAge + "\n" +
                passengerType + "\n" + "no";
        InputStream in = new ByteArrayInputStream(information.getBytes());
        System.setIn(in);
    }

    @When("system is called")
    public void systemIsCalled() {
        Application.main(new String[]{});
    }

    @Then("passenger is added to fly {string}")
    public void passengerIsAddedToFly(String flightNumber) {
        String filePath = "passengerData.csv"; // Replace with the actual path to your file

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder actualLine = new StringBuilder();
            br.lines().forEach(actualLine::append);
            assertThat(actualLine).contains(flightNumber);
            assertThat(actualLine).contains(passengerPassport);
            assertThat(actualLine).contains(passengerName);
            assertThat(actualLine).contains(String.valueOf(passengerAge));
            assertThat(actualLine).contains(expectedPassengerType.name());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
