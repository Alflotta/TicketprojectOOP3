package edu.aitu.oop3.services;

import edu.aitu.oop3.entities.Event;
import edu.aitu.oop3.entities.Seat;
import edu.aitu.oop3.entities.Ticket;
import edu.aitu.oop3.exceptions.EventCancelledException;
import edu.aitu.oop3.exceptions.SeatAlreadyBookedException;
import edu.aitu.oop3.repositories.EventRepository;
import edu.aitu.oop3.repositories.SeatRepository;
import edu.aitu.oop3.repositories.TicketRepository;

public class TicketService {

    private final EventRepository eventRepo;
    private final SeatRepository seatRepo;
    private final TicketRepository ticketRepo;

    public TicketService(EventRepository eventRepo, SeatRepository seatRepo, TicketRepository ticketRepo) {
        this.eventRepo = eventRepo;
        this.seatRepo = seatRepo;
        this.ticketRepo = ticketRepo;
    }

    public void buyTicket(int eventId, int seatId, int customerId, String ticketCode) {
        Event event = eventRepo.findById(eventId);
        if (event == null) throw new RuntimeException("Event not found");

        if (event.isCancelled()) throw new EventCancelledException();

        Seat seat = seatRepo.findById(seatId);
        if (seat == null) throw new RuntimeException("Seat not found");

        if (seat.getEventId() != eventId) throw new RuntimeException("Seat does not belong to this event");

        if (seat.isReserved()) throw new SeatAlreadyBookedException();

        // reserve seat once
        seatRepo.reserve(seatId);

        // save ticket
        Ticket ticket = new Ticket(eventId, seatId, customerId, ticketCode);
        ticketRepo.save(ticket);
    }
}

