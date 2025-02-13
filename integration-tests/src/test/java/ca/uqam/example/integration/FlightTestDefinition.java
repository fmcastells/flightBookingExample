package ca.uqam.example.integration;

import ca.uqam.mgl7230.tp1.Application;
import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp1.adapter.persist.SavePassengerInFlight;
import ca.uqam.mgl7230.tp1.adapter.plane.PlaneCatalog;
import ca.uqam.mgl7230.tp1.config.ApplicationInitializer;
import ca.uqam.mgl7230.tp1.config.FileWriterProvider;
import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp1.model.plane.PlaneType;
import ca.uqam.mgl7230.tp1.service.BookingService;
import ca.uqam.mgl7230.tp1.service.FlightPassengerService;
import ca.uqam.mgl7230.tp1.service.PassengerService;
import ca.uqam.mgl7230.tp1.service.prompt.FlightPromptService;
import ca.uqam.mgl7230.tp1.service.prompt.PassengerPromptService;
import ca.uqam.mgl7230.tp1.utils.DistanceCalculator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;

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
    private Scanner scanner;

    @Before
    public void setup() {
        mockApplicationInitializer = mockStatic(ApplicationInitializer.class);
        mockApplicationInitializer.when(ApplicationInitializer::init).then(invocationOnMock -> null);
        mockFlightCatalog = mock(FlightCatalog.class);
        mockPlaneCatalog = mock(PlaneCatalog.class);
    }

    @After
    public void tearDown() {
        mockApplicationInitializer.reset();
        mockApplicationInitializer.close();
    }

    @Given("passenger selects flight {string}")
    public void passengerSelectsFlight(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Given("flight catalog is mocked")
    public void flightCatalogIsMocked() {
        given(mockFlightCatalog.getFlightInformation(flightNumber))
                .willReturn(new FlightInformation(
                        flightNumber, 45.508888, -73.561668, -23.533773, -46.625290,
                        PlaneType.BOEING));
    }

    @Given("plane catalog is mocked where number of first class seats is {int}, number of business class seats is {int} and number of economy seats is {int}")
    public void planeCatalogIsMocked(int numberOfFirstClassSeats, int numberOfBusinessClassSeats, int numberOfEconomyClassSeats) {
        given(mockPlaneCatalog.getNumberSeatsFirstClass(PlaneType.BOEING)).willReturn(numberOfFirstClassSeats);
        given(mockPlaneCatalog.getNumberSeatsBusinessClass(PlaneType.BOEING)).willReturn(numberOfBusinessClassSeats);
        given(mockPlaneCatalog.getNumberSeatsEconomyClass(PlaneType.BOEING)).willReturn(numberOfEconomyClassSeats);
        given(mockPlaneCatalog.getNumberOfTotalSeats(PlaneType.BOEING)).willReturn(numberOfFirstClassSeats + numberOfBusinessClassSeats + numberOfEconomyClassSeats);

        FlightPassengerService flightPassengerService = new FlightPassengerService(mockPlaneCatalog, mockFlightCatalog);
        FlightPromptService flightPromptService = new FlightPromptService(mockFlightCatalog);
        PassengerPromptService passengerPromptService = new PassengerPromptService();
        PassengerService passengerService = new PassengerService(new DistanceCalculator());
        BookingService bookingService = new BookingService(flightPassengerService, passengerService);
        SavePassengerInFlight savePassengerInFlight = new SavePassengerInFlight();
        scanner = mock(Scanner.class);
        FileWriterProvider fileWriterProvider = new FileWriterProvider();

        mockApplicationInitializer.when(ApplicationInitializer::flightPassengerService)
                .thenReturn(flightPassengerService);
        mockApplicationInitializer.when(ApplicationInitializer::flightCatalog)
                        .thenReturn(mockFlightCatalog);
        mockApplicationInitializer.when(ApplicationInitializer::flightPromptService)
                .thenReturn(flightPromptService);
        mockApplicationInitializer.when(ApplicationInitializer::passengerPromptService)
                .thenReturn(passengerPromptService);
        mockApplicationInitializer.when(ApplicationInitializer::passengerService)
                .thenReturn(passengerService);
        mockApplicationInitializer.when(ApplicationInitializer::bookingService)
                .thenReturn(bookingService);
        mockApplicationInitializer.when(ApplicationInitializer::savePassengerInFlight)
                .thenReturn(savePassengerInFlight);
        mockApplicationInitializer.when(ApplicationInitializer::scanner)
                .thenReturn(scanner);
        mockApplicationInitializer.when(ApplicationInitializer::fileWriterProvider)
                .thenReturn(fileWriterProvider);
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

    @When("agent pass all information to the system")
    public void agentPassAllInformationToTheSystem() {
        given(scanner.nextLine())
                .willReturn(flightNumber)
                .willReturn(passengerPassport)
                .willReturn(passengerName)
                .willReturn(String.valueOf(passengerAge))
                .willReturn(passengerType)
                .willReturn("no");
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
