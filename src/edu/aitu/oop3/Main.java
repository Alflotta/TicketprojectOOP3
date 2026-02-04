package edu.aitu.oop3;

import edu.aitu.oop3.builders.EventBuilder;
import edu.aitu.oop3.entities.Customer;
import edu.aitu.oop3.entities.Event;
import edu.aitu.oop3.entities.Seat;
import edu.aitu.oop3.entities.Ticket;
import edu.aitu.oop3.exceptions.AuthenticationException;
import edu.aitu.oop3.repositories.CustomerRepository;
import edu.aitu.oop3.repositories.EventRepository;
import edu.aitu.oop3.repositories.SeatRepository;
import edu.aitu.oop3.repositories.TicketRepository;
import edu.aitu.oop3.repositories.impl.CustomerRepositoryImpl;
import edu.aitu.oop3.repositories.impl.EventRepositoryImpl;
import edu.aitu.oop3.repositories.impl.SeatRepositoryImpl;
import edu.aitu.oop3.repositories.impl.TicketRepositoryImpl;
import edu.aitu.oop3.search.Filters;
import edu.aitu.oop3.services.AuthService;
import edu.aitu.oop3.services.TicketService;
import edu.aitu.oop3.tickets.TicketType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        CustomerRepository customerRepo = new CustomerRepositoryImpl();
        EventRepository eventRepo = new EventRepositoryImpl();
        SeatRepository seatRepo = new SeatRepositoryImpl();
        TicketRepository ticketRepo = new TicketRepositoryImpl();

        AuthService authService = new AuthService(customerRepo);
        TicketService ticketService = new TicketService(eventRepo, seatRepo, ticketRepo);

        Scanner sc = new Scanner(System.in);
        Customer currentUser = null;

        while (true) {

            System.out.println("     EVENT TICKETING SYSTEM   ");

            if (currentUser == null) {
                System.out.println("1) Login");
                System.out.println("0) Exit");
                System.out.print("Choose option: ");

                int choice = readInt(sc);

                if (choice == 0) {
                    System.out.println("Goodbye!");
                    break;
                }

                if (choice == 1) {
                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Password: ");
                    String password = sc.nextLine();

                    try {
                        currentUser = authService.login(email, password);
                        System.out.println("Login successful.");
                    } catch (AuthenticationException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else {
                    System.out.println("Invalid option.");
                }

            } else {

                System.out.println("Logged in as: " + currentUser.getName());
                System.out.println("1) Show all events ");
                System.out.println("2) Show seats by event ID");
                System.out.println("3) Buy ticket ");
                System.out.println("4) Show all tickets purchased by me  ");
                System.out.println("5) Builder demo: create event object");
                System.out.println("6) Logout");
                System.out.println("0) Exit");
                System.out.print("Choose option: ");

                int choice = readInt(sc);

                if (choice == 0) {
                    System.out.println("Goodbye!");
                    break;
                }


                switch (choice) {

                    case 1 -> {
                        List<Event> events = eventRepo.findAll();
                        if (events.isEmpty()) {
                            System.out.println("No events found.");
                        } else {
                            for (Event e : events) {
                                System.out.println(
                                        "ID: " + e.getId() +
                                                " | " + e.getName() +
                                                " | " + e.getLocation() +
                                                " | " + e.getDate() +
                                                " | Price: " + e.getBasePrice()
                                );
                            }
                        }
                    }

                    case 2 -> {
                        System.out.print("Enter event ID: ");
                        int eventId = readInt(sc);

                        List<Seat> seats = seatRepo.findByEventId(eventId);

                        var result = Filters.filter(seats, s -> !s.isReserved());

                        if (result.getItems().isEmpty()) {
                            System.out.println("No available seats for this event.");
                        } else {
                            for (Seat s : result.getItems()) {
                                String status = s.isReserved() ? "BOOKED" : "AVAILABLE";
                                System.out.println(
                                        "SeatID: " + s.getId() +
                                                " | Seat: " + s.getSeatNumber() +
                                                " | Status: " + status
                                );

                            }
                            System.out.println("Total available seats: " + result.getTotal());
                        }
                    }

                    case 3 -> {
                        System.out.print("Event ID: ");
                        int eventId = readInt(sc);

                        System.out.print("Seat ID: ");
                        int seatId = readInt(sc);

                        System.out.println("Ticket type:");
                        System.out.println("1) STANDARD ~ base price");
                        System.out.println("2) VIP ~ +30%");
                        System.out.println("3) STUDENT ~ -15%");
                        System.out.print("Choose type: ");
                        int t = readInt(sc);

                        TicketType type = switch (t) {
                            case 2 -> TicketType.VIP;
                            case 3 -> TicketType.STUDENT;
                            default -> TicketType.STANDARD;
                        };

                        try {
                            String code = UUID.randomUUID().toString();
                            ticketService.buyTicket(eventId, seatId, currentUser.getId(), code, type);
                            System.out.println("Ticket purchased successfully.");
                            System.out.println("Ticket code: " + code);
                            System.out.println("Ticket type: " + type.name());
                        } catch (RuntimeException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }

                    case 4 -> {
                        List<Ticket> myTickets = ticketRepo.findByCustomerId(currentUser.getId());
                        if (myTickets.isEmpty()) {
                            System.out.println("You have no tickets.");
                        } else {
                            for (Ticket t : myTickets) {
                                System.out.println(
                                        "TicketID: " + t.getId() +
                                                " | EventID: " + t.getEventId() +
                                                " | SeatID: " + t.getSeatId() +
                                                " | Type: " + t.getTicketType() +
                                                " | Price: " + t.getPrice() +
                                                " | Code: " + t.getTicketCode()
                                );
                            }
                        }
                    }

                    case 5 -> {
                        Event demo = new EventBuilder()
                                .title("Demo Event")
                                .venue("Demo Venue")
                                .schedule(LocalDateTime.now().plusDays(7))
                                .cancelled(false)
                                .build();

                        System.out.println("Builder demo object created:");
                        System.out.println(
                                "Title: " + demo.getName() +
                                        " | Venue: " + demo.getLocation() +
                                        " | Date: " + demo.getDate() +
                                        " | Cancelled: " + demo.isCancelled()
                        );
                        System.out.println("This demonstrates Builder pattern.");
                    }

                    case 6 -> {
                        currentUser = null;
                        System.out.println("Logged out.");
                    }

                    default -> System.out.println("Invalid option.");
                }
            }
        }
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }
}
