package edu.aitu.oop3;

import edu.aitu.oop3.config.DiscountManager;
import edu.aitu.oop3.entities.Customer;
import edu.aitu.oop3.entities.Event;
import edu.aitu.oop3.entities.Seat;
import edu.aitu.oop3.entities.Ticket;
import edu.aitu.oop3.entities.TicketType;
import edu.aitu.oop3.exceptions.AuthenticationException;
import edu.aitu.oop3.repositories.CustomerRepository;
import edu.aitu.oop3.repositories.EventRepository;
import edu.aitu.oop3.repositories.SeatRepository;
import edu.aitu.oop3.repositories.TicketRepository;
import edu.aitu.oop3.repositories.impl.CustomerRepositoryImpl;
import edu.aitu.oop3.repositories.impl.EventRepositoryImpl;
import edu.aitu.oop3.repositories.impl.SeatRepositoryImpl;
import edu.aitu.oop3.repositories.impl.TicketRepositoryImpl;
import edu.aitu.oop3.services.AuthService;
import edu.aitu.oop3.services.TicketService;
import edu.aitu.oop3.utils.SearchResult;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        // Repositories
        CustomerRepository customerRepo = new CustomerRepositoryImpl();
        EventRepository eventRepo = new EventRepositoryImpl();
        SeatRepository seatRepo = new SeatRepositoryImpl();
        TicketRepository ticketRepo = new TicketRepositoryImpl();

        // Services
        AuthService authService = new AuthService(customerRepo);
        TicketService ticketService = new TicketService(eventRepo, seatRepo, ticketRepo);

        Scanner sc = new Scanner(System.in);
        Customer currentUser = null;

        while (true) {

            System.out.println("\n==============================");
            System.out.println("     EVENT TICKETING SYSTEM   ");
            System.out.println("==============================");

            // -------- NOT LOGGED IN --------
            if (currentUser == null) {

                System.out.println("1) Login");
                System.out.println("0) Exit");
                System.out.print("Choose option: ");

                int choice = readIntSafe(sc);

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
                        System.out.println("Welcome, " + currentUser.getName() + "!");
                    } catch (AuthenticationException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else {
                    System.out.println("Invalid option.");
                }

            }
            // -------- LOGGED IN --------
            else {

                System.out.println("Logged in as: " + currentUser.getName());
                System.out.println("1) Show all events ");
                System.out.println("2) Show seats by event ID");
                System.out.println("3) Buy ticket ");
                System.out.println("4) Show all tickets ");
                System.out.println("5)  Search events by location");
                System.out.println("6) Logout ");
                System.out.println("0) Exit");
                System.out.print("Choose option: ");

                int choice = readIntSafe(sc);

                if (choice == 0) {
                    System.out.println("Goodbye!");
                    break;
                }

                switch (choice) {

                    // ✅ Generics: SearchResult<Event>
                    case 1: {
                        SearchResult<Event> result = ticketService.getAllEvents();
                        if (result.isEmpty()) {
                            System.out.println("No events found.");
                        } else {
                            for (Event e : result.getItems()) {
                                System.out.println(
                                        "ID: " + e.getId() +
                                                " | " + e.getName() +
                                                " | " + e.getLocation() +
                                                " | " + e.getDate()
                                );
                            }
                            System.out.println("Total events: " + result.count());
                        }
                        break;
                    }

                    case 2: {
                        System.out.print("Enter event ID: ");
                        int eventId = readIntSafe(sc);

                        List<Seat> seats = seatRepo.findByEventId(eventId);
                        if (seats.isEmpty()) {
                            System.out.println("No seats for this event.");
                        } else {
                            for (Seat s : seats) {
                                System.out.println(
                                        "SeatID: " + s.getId() +
                                                " | " + s.getSeatNumber() +
                                                " | reserved = " + s.isReserved()
                                );
                            }
                        }
                        break;
                    }

                    // ✅ Factory + Singleton + жаңа buyTicket(...)
                    case 3: {
                        System.out.print("Event ID: ");
                        int eventId = readIntSafe(sc);

                        System.out.print("Seat ID: ");
                        int seatId = readIntSafe(sc);

                        TicketType type = chooseTicketType(sc);

                        // ✅ Singleton usage (паттерн көрінсін)
                        double discount = DiscountManager.getInstance().getDiscount(type.name());
                        System.out.println("Ticket type: " + type + " | Discount: " + (int) (discount * 100) + "%");

                        try {
                            String code = UUID.randomUUID().toString();
                            ticketService.buyTicket(eventId, seatId, currentUser.getId(), code, type);
                            System.out.println("Ticket purchased successfully!");
                            System.out.println("Ticket code: " + code);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    }

                    // ✅ Generics: SearchResult<Ticket>
                    case 4: {
                        SearchResult<Ticket> result = ticketService.getAllTickets();
                        if (result.isEmpty()) {
                            System.out.println("No tickets found.");
                        } else {
                            for (Ticket t : result.getItems()) {
                                System.out.println(
                                        "TicketID: " + t.getId() +
                                                " | EventID: " + t.getEventId() +
                                                " | SeatID: " + t.getSeatId() +
                                                " | Code: " + t.getTicketCode()
                                );
                            }
                            System.out.println("Total tickets: " + result.count());
                        }
                        break;
                    }

                    // ✅ Generics filter
                    case 5: {
                        System.out.print("Enter location keyword: ");
                        String kw = sc.nextLine().trim().toLowerCase();

                        SearchResult<Event> filtered = ticketService.getAllEvents()
                                .filter(e -> e.getLocation() != null &&
                                        e.getLocation().toLowerCase().contains(kw));

                        if (filtered.isEmpty()) {
                            System.out.println("No events found for this keyword.");
                        } else {
                            for (Event e : filtered.getItems()) {
                                System.out.println(
                                        "ID: " + e.getId() +
                                                " | " + e.getName() +
                                                " | " + e.getLocation() +
                                                " | " + e.getDate()
                                );
                            }
                            System.out.println("Found: " + filtered.count());
                        }
                        break;
                    }
                    case 6: {
                        currentUser = null;
                        System.out.println("Logged out successfully.");
                        break;
                    }

                    default:
                        System.out.println("Invalid option.");
                }
            }
        }
    }


    private static int readIntSafe(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Enter a number: ");
            }
        }
    }

    private static TicketType chooseTicketType(Scanner sc) {
        System.out.println("Choose ticket type:");
        System.out.println("1) STANDARD");
        System.out.println("2) VIP");
        System.out.println("3) STUDENT");
        System.out.print("Type: ");

        int t = readIntSafe(sc);

        if (t == 2) return TicketType.VIP;
        if (t == 3) return TicketType.STUDENT;
        return TicketType.STANDARD;
    }
}
