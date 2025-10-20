package com.example.hotelmanagement.data;

import com.example.hotelmanagement.model.*;
import com.example.hotelmanagement.repository.*;
import com.example.hotelmanagement.service.RoomService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class SampleDataLoader implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(SampleDataLoader.class);

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomFeatureRepository roomFeatureRepository;
    @Autowired
    private RoomService roomService;

    private final Random random = new Random();

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        // clear existing data
        bookingRepository.deleteAll();
        clientRepository.deleteAll();
        employeeRepository.deleteAll();
        mealRepository.deleteAll();
        reviewRepository.deleteAll();
        roomRepository.deleteAll();


        // sample roomFeatures
        RoomFeature wifi = roomFeatureRepository.save(new RoomFeature("WiFi"));
        RoomFeature airConditioning = roomFeatureRepository.save(new RoomFeature("Air Conditioning"));
        RoomFeature balcony = roomFeatureRepository.save(new RoomFeature("Balcony"));
        RoomFeature minibar = roomFeatureRepository.save(new RoomFeature("Minibar"));
        RoomFeature seaView = roomFeatureRepository.save(new RoomFeature("Sea View"));
        RoomFeature tv = roomFeatureRepository.save(new RoomFeature("TV"));
        RoomFeature coffeeMaker = roomFeatureRepository.save(new RoomFeature("Coffee Maker"));
        RoomFeature safe = roomFeatureRepository.save(new RoomFeature("Safe"));
        RoomFeature queenSizeBed = roomFeatureRepository.save(new RoomFeature("Queen Size Bed"));
        RoomFeature singleBed = roomFeatureRepository.save(new RoomFeature("Single Bed"));
        RoomFeature doubleBed = roomFeatureRepository.save(new RoomFeature("Double Bed"));
        RoomFeature bathtub = roomFeatureRepository.save(new RoomFeature("Bathtub"));
        RoomFeature hotTub = roomFeatureRepository.save(new RoomFeature("Hot tub"));
        RoomFeature hairDryer = roomFeatureRepository.save(new RoomFeature("Hair Dryer"));
        RoomFeature roomService = roomFeatureRepository.save(new RoomFeature("Room Service"));
        RoomFeature desk = roomFeatureRepository.save(new RoomFeature("Desk"));
        RoomFeature heating = roomFeatureRepository.save(new RoomFeature("Heating"));
        RoomFeature soundproofing = roomFeatureRepository.save(new RoomFeature("Soundproofing"));
        RoomFeature petFriendly = roomFeatureRepository.save(new RoomFeature("Pet Friendly"));
        RoomFeature nonSmoking = roomFeatureRepository.save(new RoomFeature("Non-Smoking"));
        RoomFeature freeParking = roomFeatureRepository.save(new RoomFeature("Free Parking"));
        RoomFeature kitchenette = roomFeatureRepository.save(new RoomFeature("Kitchenette"));
        RoomFeature privateElevator = roomFeatureRepository.save(new RoomFeature("Private Elevator"));
        RoomFeature sofa = roomFeatureRepository.save(new RoomFeature("Sofa"));
        RoomFeature bathrobeSlippers = roomFeatureRepository.save(new RoomFeature("Bathrobe and Slippers"));

        // single rooms (8 samples/8)
        Room singleRoom1 = new Room(250.00, true, "1 single bed, cozy and quiet", RoomType.SINGLE,
                List.of(wifi, airConditioning, petFriendly, tv, desk, nonSmoking, singleBed));
        Room singleRoom2 = new Room(260.00, true, "1 single bed, city view", RoomType.SINGLE,
                List.of(wifi, balcony, tv, hairDryer, desk, airConditioning, nonSmoking, singleBed));
        Room singleRoom3 = new Room(240.00, true, "1 single bed, great for short stays", RoomType.SINGLE,
                List.of(wifi, heating, tv, safe, desk, airConditioning, nonSmoking, singleBed));
        Room singleRoom4 = new Room(255.00, false, "1 single bed, minimalistic design", RoomType.SINGLE,
                List.of(wifi, airConditioning, tv, desk, nonSmoking, singleBed));
        Room singleRoom5 = new Room(270.00, true, "1 single bed, ideal for business travel", RoomType.SINGLE,
                List.of(wifi, soundproofing, desk, tv, airConditioning, nonSmoking, singleBed));
        Room singleRoom6 = new Room(245.00, true, "1 single bed, garden view", RoomType.SINGLE,
                List.of(wifi, airConditioning, hairDryer, airConditioning, nonSmoking, singleBed));
        Room singleRoom7 = new Room(265.00, true, "1 single bed, free parking included", RoomType.SINGLE,
                List.of(wifi, freeParking, tv, airConditioning, nonSmoking, singleBed));
        Room singleRoom8 = new Room(250.00, false, "1 single bed, classic style", RoomType.SINGLE,
                List.of(wifi, tv, heating, airConditioning, nonSmoking, singleBed));

        // double rooms (13 samples/180)
        Room doubleRoom1 = new Room(399.00, true, "1 double bed, modern interior", RoomType.DOUBLE,
                List.of(wifi, airConditioning, petFriendly, tv, nonSmoking, doubleBed));
        Room doubleRoom2 = new Room(420.00, false, "1 double bed, with minibar and balcony", RoomType.DOUBLE,
                List.of(wifi, minibar, balcony, tv, safe, airConditioning, nonSmoking, doubleBed));
        Room doubleRoom3 = new Room(410.00, true, "1 double bed, comfortable for couples", RoomType.DOUBLE,
                List.of(wifi, tv, coffeeMaker, desk, heating, airConditioning, nonSmoking, doubleBed));
        Room doubleRoom4 = new Room(430.00, true, "1 double bed, Scandinavian-style design", RoomType.DOUBLE,
                List.of(wifi, airConditioning, tv, desk, heating, nonSmoking, doubleBed));
        Room doubleRoom5 = new Room(415.00, false, "1 double bed, ideal for romantic getaway", RoomType.DOUBLE,
                List.of(wifi, balcony, tv, hairDryer, minibar, airConditioning, nonSmoking, doubleBed));
        Room doubleRoom6 = new Room(445.00, true, "1 double bed, quiet room with garden view", RoomType.DOUBLE,
                List.of(wifi, tv, safe, airConditioning, desk, nonSmoking, doubleBed));
        Room doubleRoom7 = new Room(400.00, true, "1 double bed, minimalist and modern", RoomType.DOUBLE,
                List.of(wifi, tv, desk, soundproofing, airConditioning, nonSmoking, doubleBed));
        Room doubleRoom8 = new Room(460.00, false, "1 double bed, premium amenities included", RoomType.DOUBLE,
                List.of(wifi, minibar, roomService, airConditioning, safe, nonSmoking, doubleBed));
        Room doubleRoom9 = new Room(410.00, true, "1 double bed, smart TV and workspace", RoomType.DOUBLE,
                List.of(wifi, tv, desk, coffeeMaker, airConditioning, nonSmoking, doubleBed));
        Room doubleRoom10 = new Room(440.00, true, "1 double bed, high floor", RoomType.DOUBLE,
                List.of(wifi, airConditioning, tv, balcony, soundproofing, nonSmoking, doubleBed));
        Room doubleRoom11 = new Room(390.00, true, "1 double bed, eco-friendly decor", RoomType.DOUBLE,
                List.of(wifi, tv, heating, petFriendly, airConditioning, nonSmoking, doubleBed));
        Room doubleRoom12 = new Room(420.00, false, "1 double bed, with bathtub and minibar", RoomType.DOUBLE,
                List.of(wifi, tv, minibar, bathtub, hairDryer, airConditioning, nonSmoking, doubleBed));
        Room doubleRoom13 = new Room(435.00, true, "1 double bed, with lounge chair and large windows", RoomType.DOUBLE,
                List.of(wifi, airConditioning, tv, desk, coffeeMaker, nonSmoking, doubleBed));

        // junior suite rooms (2 samples/20)
        Room juniorSuite1 = new Room(550.00, true, "Queen bed + small living room with sofa", RoomType.JUNIOR_SUITE,
                List.of(wifi, tv, coffeeMaker, hairDryer, desk, queenSizeBed, airConditioning, bathrobeSlippers, nonSmoking, sofa));
        Room juniorSuite2 = new Room(570.00, true, "Stylish junior suite with sea view", RoomType.JUNIOR_SUITE,
                List.of(wifi, seaView, tv, airConditioning, queenSizeBed, hairDryer, minibar, safe, coffeeMaker, bathrobeSlippers, nonSmoking, sofa));

        // medium suite rooms (5 samples/5)
        Room mediumSuite1 = new Room(750.00, true, "2 queen bedrooms + living room", RoomType.MEDIUM_SUITE,
                List.of(wifi, tv, airConditioning, safe, hairDryer, queenSizeBed, roomService, desk, coffeeMaker, bathrobeSlippers, sofa, heating));
        Room mediumSuite2 = new Room(780.00, true, "Spacious suite for family or friends", RoomType.MEDIUM_SUITE,
                List.of(wifi, coffeeMaker, bathtub, minibar, hairDryer, queenSizeBed, soundproofing, petFriendly, roomService, bathrobeSlippers, sofa, heating));
        Room mediumSuite3 = new Room(770.00, true, "Elegant suite with two queen beds and lounge area", RoomType.MEDIUM_SUITE,
                List.of(wifi, tv, airConditioning, queenSizeBed, sofa, minibar, desk, coffeeMaker, bathrobeSlippers, soundproofing, heating));
        Room mediumSuite4 = new Room(790.00, false, "Perfect for families – 2 bedrooms and relaxation zone", RoomType.MEDIUM_SUITE,
                List.of(wifi, tv, airConditioning, petFriendly, queenSizeBed, bathtub, hairDryer, roomService, sofa, heating));
        Room mediumSuite5 = new Room(765.00, true, "Modern medium suite with workspace and comfort", RoomType.MEDIUM_SUITE,
                List.of(wifi, tv, desk, queenSizeBed, coffeeMaker, safe, sofa, bathrobeSlippers, airConditioning, hairDryer, heating));

        // large suite rooms (5 samples/30)
        Room largeSuite1 = new Room(890.00, true, "1 queen + 3 single beds, 2 bedrooms, great for groups", RoomType.LARGE_SUITE,
                List.of(wifi, airConditioning, tv, freeParking, kitchenette,  queenSizeBed, roomService, desk, heating, bathrobeSlippers, hotTub, sofa, hairDryer));
        Room largeSuite2 = new Room(920.00, true, "Large suite with garden view and kitchenette", RoomType.LARGE_SUITE,
                List.of(wifi, kitchenette, tv, hairDryer, petFriendly, queenSizeBed, minibar, heating, roomService, airConditioning, bathrobeSlippers, hotTub, sofa));
        Room largeSuite3 = new Room(910.00, false, "Spacious suite ideal for families or groups", RoomType.LARGE_SUITE,
                List.of(wifi, airConditioning, queenSizeBed, sofa, tv, freeParking, kitchenette, desk, roomService, bathrobeSlippers, hotTub, heating));
        Room largeSuite4 = new Room(940.00, true, "Two-bedroom suite with premium amenities and hot tub", RoomType.LARGE_SUITE,
                List.of(wifi, tv, queenSizeBed, hairDryer, kitchenette, roomService, minibar, desk, hotTub, bathrobeSlippers, sofa, heating, soundproofing));
        Room largeSuite5 = new Room(899.00, true, "Stylish large suite with full comfort and workspace", RoomType.LARGE_SUITE,
                List.of(wifi, queenSizeBed, airConditioning, tv, desk, roomService, kitchenette, bathrobeSlippers, sofa, freeParking, heating, hairDryer, petFriendly));

        // presidential suite rooms (1sample/1)
        Room presidentialSuite = new Room(2000.00, true, "3 bedrooms (3 queen beds), 2 private bathrooms, private elevators, hot tub, luxurious comfort", RoomType.PRESIDENTIAL_SUITE,
                List.of(wifi, airConditioning, queenSizeBed, safe, minibar, soundproofing, roomService, tv, balcony, privateElevator, hotTub,
                        bathrobeSlippers, sofa, kitchenette, hairDryer, seaView, desk, coffeeMaker, petFriendly, heating, freeParking, bathtub));

        roomRepository.saveAll(List.of(singleRoom1, singleRoom2, singleRoom3, singleRoom4, singleRoom5, singleRoom6, singleRoom7, singleRoom8));
        roomRepository.saveAll(List.of(doubleRoom1, doubleRoom2, doubleRoom3, doubleRoom4, doubleRoom5, doubleRoom6, doubleRoom7, doubleRoom8, doubleRoom9, doubleRoom10, doubleRoom11, doubleRoom12, doubleRoom13));
        roomRepository.saveAll(List.of(juniorSuite1, juniorSuite2));
        roomRepository.saveAll(List.of(mediumSuite1, mediumSuite2, mediumSuite3, mediumSuite4, mediumSuite5));
        roomRepository.saveAll(List.of(largeSuite1, largeSuite2, largeSuite3, largeSuite4, largeSuite5));
        roomRepository.saveAll(List.of(presidentialSuite));

        // sample Employees
        employeeRepository.saveAll(List.of(
                new Employee("Anna", "Nowak", LocalDate.of(1980, 3, 15), "anna.nowak@example.com", "500100100", JobTitle.CEO, 40000),
                new Employee("Jan", "Kowalski", LocalDate.of(1985, 5, 10), "jan.kowalski@example.com", "500200200", JobTitle.MANAGER, 18000),
                new Employee("Ewa", "Dąbrowska", LocalDate.of(1994, 10, 28), "ewa.dabrowska@example.com", "500900900", JobTitle.RECEPTIONIST, 6200),
                new Employee("Katarzyna", "Zielińska", LocalDate.of(1990, 7, 20), "k.zielinska@example.com", "500300300", JobTitle.NIGHT_DUTY_MANAGER, 14000),
                new Employee("Tomasz", "Wiśniewski", LocalDate.of(1982, 9, 12), "t.wisniewski@example.com", "500400400", JobTitle.CHEF, 12000),
                new Employee("Paulina", "Wójcik", LocalDate.of(1992, 6, 5), "paulina.wojcik@example.com", "500500500", JobTitle.COOK, 7500),
                new Employee("Piotr", "Kamiński", LocalDate.of(1988, 1, 18), "piotr.kaminski@example.com", "500600600", JobTitle.HOUSEKEEPER, 5200),
                new Employee("Magdalena", "Lewandowska", LocalDate.of(1991, 8, 30), "magda.lewandowska@example.com", "500700700", JobTitle.FRONT_OFFICE, 6900),
                new Employee("Grzegorz", "Szymański", LocalDate.of(1986, 12, 9), "grzegorz.szymanski@example.com", "500800800", JobTitle.SPA_MANAGER, 11000),
                new Employee("Ewa", "Dąbrowska", LocalDate.of(1994, 10, 28), "ewa.dabrowska@example.com", "500900900", JobTitle.RECEPTIONIST, 6200),
                new Employee("Mateusz", "Król", LocalDate.of(1993, 11, 14), "mateusz.krol@example.com", "501000001", JobTitle.SOCIAL_MEDIA_CREATOR, 7800),
                new Employee("Robert", "Bąk", LocalDate.of(1986, 3, 10), "robert.bak@example.com", "501000002", JobTitle.PORTER, 5900),
                new Employee("Natalia", "Górska", LocalDate.of(1991, 6, 3), "natalia.gorska@example.com", "501000003", JobTitle.CONCIERGE, 7200),
                new Employee("Łukasz", "Walczak", LocalDate.of(1990, 8, 20), "lukasz.w@example.com", "501000004", JobTitle.BARTENDER, 6700),
                new Employee("Iwona", "Kubiak", LocalDate.of(1984, 5, 29), "iwona.kubiak@example.com", "501000005", JobTitle.HR_MANAGER, 10000),
                new Employee("Kamil", "Sikora", LocalDate.of(1987, 7, 6), "kamil.sikora@example.com", "501000006", JobTitle.IT_MANAGER, 15000),
                new Employee("Barbara", "Król", LocalDate.of(1992, 9, 8), "barbara.krol@example.com", "501000007", JobTitle.IT_ANALYST, 10800),
                new Employee("Damian", "Kozłowski", LocalDate.of(1996, 10, 19), "damian.kozlowski@example.com", "501000008", JobTitle.HELP_DESK_TECHNICIAN, 7200),
                new Employee("Renata", "Mazur", LocalDate.of(1993, 11, 30), "renata.mazur@example.com", "501000009", JobTitle.IT_SPECIALIST, 11200),
                new Employee("Piotr", "Lis", LocalDate.of(1989, 3, 12), "piotr.lis@example.com", "501000010", JobTitle.NETWORK_ENGINEER, 12500),
                new Employee("Anita", "Sadowska", LocalDate.of(1990, 1, 25), "anita.sadowska@example.com", "501000011", JobTitle.CYBERSECURITY_SPECIALIST, 14500),
                new Employee("Tomasz", "Pawlak", LocalDate.of(1991, 2, 14), "tomasz.pawlak@example.com", "501000012", JobTitle.SOFTWARE_DEVELOPER, 13800),
                new Employee("Joanna", "Cieślak", LocalDate.of(1986, 6, 21), "joanna.cieslak@example.com", "501000013", JobTitle.SYSTEM_ADMINISTRATOR, 12300),
                new Employee("Grzegorz", "Zając", LocalDate.of(1983, 12, 9), "grzegorz.zajac@example.com", "501000014", JobTitle.DATABASE_ADMINISTRATOR, 12800),
                new Employee("Marta", "Kurek", LocalDate.of(1995, 4, 18), "marta.kurek@example.com", "501000015", JobTitle.ACCOUNTING_MANAGER, 9800),
                new Employee("Adrian", "Urbański", LocalDate.of(1988, 7, 13), "adrian.urbanski@example.com", "501000016", JobTitle.SALES_MANAGER, 10400),
                new Employee("Karol", "Szymański", LocalDate.of(1987, 8, 5), "karol.szymanski@example.com", "501000017", JobTitle.SECURITY_MANAGER, 8900),
                new Employee("Wojciech", "Baran", LocalDate.of(1989, 9, 15), "wojciech.baran@example.com", "501000018", JobTitle.GUARD, 5700),
                new Employee("Dorota", "Stępień", LocalDate.of(1992, 10, 10), "dorota.stepien@example.com", "501000019", JobTitle.ACCOUNTANT, 7900),
                new Employee("Sebastian", "Wilk", LocalDate.of(1996, 3, 3), "sebastian.wilk@example.com", "501000020", JobTitle.PARKING_ATTENDANT, 5100),
                new Employee("Izabela", "Sawicka", LocalDate.of(1991, 5, 23), "izabela.sawicka@example.com", "501000021", JobTitle.EVENT_PLANNER, 9600),
                new Employee("Bartłomiej", "Michalak", LocalDate.of(1994, 12, 7), "bart.michalak@example.com", "501000022", JobTitle.WAITER, 5600),
                new Employee("Natalia", "Jabłońska", LocalDate.of(1990, 6, 16), "natalia.jablonska@example.com", "501000023", JobTitle.MAINTENANCE_TECHNICIAN, 7300),
                new Employee("Michał", "Rutkowski", LocalDate.of(1985, 4, 1), "michal.rutkowski@example.com", "501000024", JobTitle.BEVERAGE_MANAGER, 8100),
                new Employee("Aleksandra", "Nowicka", LocalDate.of(1993, 2, 28), "aleksandra.nowicka@example.com", "501000025", JobTitle.LAUNDRY_ATTENDANT, 5300)
        ));

        // sample clients
        clientRepository.saveAll(List.of(
                new Client("Anna", "Kowalska", LocalDate.of(1990, 3, 15), "anna.kowalska@example.com", "600123456"),
                new Client("Jan", "Nowak", LocalDate.of(1985, 6, 22), "jan.nowak@example.com", "691234567"),
                new Client("Katarzyna", "Wiśniewska", LocalDate.of(1992, 11, 5), "k.wisniewska@example.com", "789346678"),
                new Client("Tomasz", "Wójcik", LocalDate.of(1988, 1, 30), "client@hotel.com", "512456789"),
                new Client("Maria", "Kamińska", LocalDate.of(1995, 7, 12), "m.kaminska@example.com", "602567890"),
                new Client("Michał", "Lewandowski", LocalDate.of(1983, 2, 18), "michal.lewandowski@example.com", "609678901"),
                new Client("Joanna", "Zielińska", LocalDate.of(1998, 9, 3), "joanna.zielinska@example.com", "604789012"),
                new Client("Paweł", "Szymański", LocalDate.of(1986, 12, 20), "pawel.szymanski@example.com", "605890123"),
                new Client("Agnieszka", "Woźniak", LocalDate.of(1993, 4, 8), "agnieszka.wozniak@example.com", "606901234"),
                new Client("Krzysztof", "Dąbrowski", LocalDate.of(1991, 10, 27), "krzysztof.dabrowski@example.com", "607012345")
        ));

        // sample meals
        mealRepository.saveAll(List.of(
                new Meal(MealPlan.ALL_INCLUSIVE, "All meals and drinks included", 40.0, 65.0, 60.0),
                new Meal(MealPlan.FULL_BOARD, "Breakfast, lunch, and dinner included",  38.0, 60.0, 55.0),
                new Meal(MealPlan.HALF_BOARD, "Breakfast and dinner included", 35.0, 0.0, 50.0),
                new Meal(MealPlan.BED_AND_BREAKFAST, "Only breakfast included", 32.0, 0.0, 0.0),
                new Meal(MealPlan.ROOM_ONLY, "No meals included; room without kitchen", 0.0, 0.0, 0.0),
                new Meal(MealPlan.SELF_CATERING, "No meals; room with kitchen facilities", 0.0, 0.0, 0.0),

                new Meal(MealPlan.ALL_INCLUSIVE, "Family special: all meals, snacks, and drinks", 42.0, 68.0, 63.0),
                new Meal(MealPlan.FULL_BOARD, "Full 3 meals: breakfast, lunch and dinner", 40.0, 65.0, 60.0),
                new Meal(MealPlan.HALF_BOARD, "Romantic dinner package", 34.0, 0.0, 52.0),
                new Meal(MealPlan.BED_AND_BREAKFAST, "Simple morning meal plan", 30.0, 0.0, 0.0)
        ));
        // generate random bookings
        generateRandomBookings(5);
    }

    private void generateRandomBookings(int numOfBookings) {
        List<Client> clients = clientRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
        List<Meal> meals = mealRepository.findAll();
        List<Room> rooms = roomRepository.findAll();
        BookingStatus[] statuses = BookingStatus.values();

        if (clients.isEmpty() || employees.isEmpty() || meals.isEmpty() || rooms.isEmpty() || statuses.length == 0) {
            logger.warn("Cannot generate more bookings: either one or many required entities are missing");
            return;
        }

        List<Booking> bookings = new ArrayList<>();
        for (int i = 0; i < numOfBookings; i++) {
            // random booking date (within the last 30 days to next 30 days)
            LocalDateTime bookingDate = LocalDateTime.now()
                    .minusDays(random.nextInt(30))
                    .plusDays(random.nextInt(30));

            // random check-in date (today or up to 60 days in the future)
            LocalDate checkinDate = LocalDate.now().plusDays(random.nextInt(60));

            // random stay duration (1 to 7 nights)
            int stayDuration = random.nextInt(7) + 1;
            LocalDate checkoutDate = checkinDate.plusDays(stayDuration);

            // random client
            Client client = clients.get(random.nextInt(clients.size()));

            // random employee (preferably frontOfficeEmployee or receptionist)
            List<Employee> frontOfficeEmployee = employees.stream()
                    .filter(e-> e.getJobTitle() == JobTitle.FRONT_OFFICE || e.getJobTitle() == JobTitle.RECEPTIONIST)
                    .collect(Collectors.toList());
            Employee employee = frontOfficeEmployee.isEmpty() ? employees.get(random.nextInt(employees.size())) :
                    frontOfficeEmployee.get(random.nextInt(frontOfficeEmployee.size()));

            // random meal
            Meal meal = meals.get(random.nextInt(meals.size()));

            // select available rooms for the date range
            List<Room> availableRooms = rooms.stream()
                    .filter(room -> roomService.isRoomAvailable(room, checkinDate, checkoutDate))
                    .collect(Collectors.toList());

            if (availableRooms.isEmpty()) {
                logger.warn("No available rooms on dates {} - {}", checkinDate, checkoutDate);
                continue;
            }

            // random number of rooms connected to 1 booking
            int numRooms = random.nextInt(Math.min(3, availableRooms.size())) + 1;
            List<Room> selectedRooms = new ArrayList<>();
            Collections.shuffle(availableRooms, random);
            for (int j = 0; j < numRooms; j++) {
                selectedRooms.add(availableRooms.get(j));
            }

            // random booking status
            BookingStatus status = statuses[random.nextInt(statuses.length)];

            // create booking
            Booking booking = new Booking(
                    bookingDate,
                    checkinDate,
                    checkoutDate,
                    availableRooms,
                    client,
                    employee,
                    meal,
                    status
            );
            bookings.add(booking);
        }
        bookingRepository.saveAll(bookings);
        logger.info("Successfully generated {} random bookings", bookings.size());
    }
    // prevent overlapping bookings for the same room
    private boolean isRoomAvailable(Room room, LocalDate checkinDate, LocalDate checkoutDate) {
        return bookingRepository.findOverlappingBookingsForRoom(room, checkinDate, checkoutDate).isEmpty();
    }
}
