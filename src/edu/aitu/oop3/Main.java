package edu.aitu.oop3;

import edu.aitu.oop3.entities.Customer;
import edu.aitu.oop3.entities.Seat;
import edu.aitu.oop3.exceptions.AuthenticationException;
import edu.aitu.oop3.exceptions.EventCancelledException;
import edu.aitu.oop3.exceptions.SeatAlreadyBookedException;
import edu.aitu.oop3.repositories.CustomerRepository;
import edu.aitu.oop3.repositories.EventRepository;
import edu.aitu.oop3.repositories.SeatRepository;
import edu.aitu.oop3.repositories.TicketRepository;
import edu.aitu.oop3.repositories.impl.CustomerRepositoryImpl;
import edu.aitu.oop3.repositories.impl.EventRepositoryImpl;
import edu.aitu.oop3.repositories.impl.SeatRepositoryImpl;
import edu.aitu.oop3.repositories.impl.TicketRepositoryImpl;
import edu.aitu.oop3.services.AuthService;
import edu.aitu.oop3.services.SeatAllocationService;
import edu.aitu.oop3.services.TicketService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        // Repos
        CustomerRepository customerRepo = new CustomerRepositoryImpl();
        EventRepository eventRepo = new EventRepositoryImpl();
        SeatRepository seatRepo = new SeatRepositoryImpl();
        TicketRepository ticketRepo = new TicketRepositoryImpl();

        // Services
        AuthService authService = new AuthService(customerRepo);
        SeatAllocationService seatService = new SeatAllocationService(seatRepo);
        TicketService ticketService = new TicketService(eventRepo, seatRepo, ticketRepo);

        Scanner sc = new Scanner(System.in);
        Customer currentUser = null;

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1) Login");
            System.out.println("2) View seats by event");
            System.out.println("3) Buy ticket");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear Enter

            if (choice == 0) break;

            if (choice == 1) {
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();

                try {
                    currentUser = authService.login(email, password);
                    System.out.println("Welcome, " + currentUser.getName());
                } catch (AuthenticationException e) {
                    System.out.println("exception " + e.getMessage());
                }
            }

            if (choice == 2) {
                System.out.print("Event ID: ");
                int eventId = sc.nextInt();
                sc.nextLine();

                List<Seat> seats = seatRepo.findByEventId(eventId);
                if (seats.isEmpty()) {
                    System.out.println("No seats found.");
                } else {
                    for (Seat s : seats) {
                        System.out.println("SeatID=" + s.getId()
                                + " | " + s.getSeatNumber()
                                + " | reserved=" + s.isReserved());
                    }
                }
            }

            if (choice == 3) {
                if (currentUser == null) {
                    System.out.println("Please login first.");
                    continue;
                }

                System.out.print("Event ID: ");
                int eventId = sc.nextInt();
                System.out.print("Seat ID: ");
                int seatId = sc.nextInt();
                sc.nextLine();

                try {
                    String code = UUID.randomUUID().toString();
                    ticketService.buyTicket(eventId, seatId, currentUser.getId(), code);
                    System.out.println("Ticket bought! Code: " + code);
                } catch (SeatAlreadyBookedException | EventCancelledException e) {
                    System.out.println(e.getMessage());
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("Bye!");
    }
}

