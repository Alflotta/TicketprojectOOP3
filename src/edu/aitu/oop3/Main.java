package edu.aitu.oop3;

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
import edu.aitu.oop3.services.AuthService;
import edu.aitu.oop3.services.TicketService;

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

                int choice = sc.nextInt();
                sc.nextLine();

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
                System.out.println("1) Show all events");
                System.out.println("2) Show seats by event ID");
                System.out.println("3) Buy ticket");
                System.out.println("4) Show all tickets");
                System.out.println("5) Logout");
                System.out.println("0) Exit");
                System.out.print("Choose option: ");

                int choice = sc.nextInt();
                sc.nextLine();

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
                                                " | " + e.getDate()
                                );
                            }
                        }
                    }

                    case 2 -> {
                        System.out.print("Enter event ID: ");
                        int eventId = sc.nextInt();
                        sc.nextLine();

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
                    }

                    case 3 -> {
                        System.out.print("Event ID: ");
                        int eventId = sc.nextInt();

                        System.out.print("Seat ID: ");
                        int seatId = sc.nextInt();
                        sc.nextLine();

                        try {
                            String code = UUID.randomUUID().toString();
                            ticketService.buyTicket(eventId, seatId, currentUser.getId(), code);
                            System.out.println("Ticket purchased successfully!");
                            System.out.println("Ticket code: " + code);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }

                    case 4 -> {
                        List<Ticket> tickets = ticketRepo.findAll();
                        if (tickets.isEmpty()) {
                            System.out.println("No tickets found.");
                        } else {
                            for (Ticket t : tickets) {
                                System.out.println(
                                        "TicketID: " + t.getId() +
                                                " | EventID: " + t.getEventId() +
                                                " | SeatID: " + t.getSeatId() +
                                                " | Code: " + t.getTicketCode()
                                );
                            }
                        }
                    }

                    case 5 -> {
                        currentUser = null;
                        System.out.println("Logged out successfully.");
                    }

                    default -> System.out.println("Invalid option.");
                }
            }
        }
    }
}