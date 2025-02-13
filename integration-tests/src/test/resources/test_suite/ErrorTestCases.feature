Feature: Test Suite for error adding passenger to flight

  Scenario: Overbook the flight
    Given passenger selects flight "UQAM005"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 0, number of business class seats is 0 and number of economy seats is 2
    And service is initialized
    And a passenger with passport number:
      | PASSPORT_CAD001 |
      | PASSPORT_CAD002 |
      | PASSPORT_CAD003 |
    And name:
      | Passenger_1 |
      | Passenger_2 |
      | Passenger_3 |
    And age:
      | 10 |
      | 20 |
      | 30 |
    And looking to buy in class:
      | first |
      | first |
      | first |
    When agent pass all information to the system and continue "yes"
    And system is called
    Then passenger "Passenger_1" is added to fly "UQAM005"
    Then passenger "Passenger_2" is added to fly "UQAM005"
    Then passenger "Passenger_3" is not added to fly "UQAM005"