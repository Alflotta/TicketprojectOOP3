package edu.aitu.oop3.services;

import edu.aitu.oop3.entities.Event;
import edu.aitu.oop3.entities.Seat;
import edu.aitu.oop3.entities.Ticket;
import edu.aitu.oop3.entities.TicketType;
import edu.aitu.oop3.exceptions.EventCancelledException;
import edu.aitu.oop3.exceptions.SeatAlreadyBookedException;
import edu.aitu.oop3.factories.TicketFactory;
import edu.aitu.oop3.repositories.EventRepository;
import edu.aitu.oop3.repositories.SeatRepository;
import edu.aitu.oop3.repositories.TicketRepository;
import edu.aitu.oop3.utils.SearchResult;

import java.util.List;

public class TicketService {

    private final EventRepository eventRepo;
    private final SeatRepository seatRepo;
    private final TicketRepository ticketRepo;

    public TicketService(EventRepository eventRepo, SeatRepository seatRepo, TicketRepository ticketRepo) {
        this.eventRepo = eventRepo;
        this.seatRepo = seatRepo;
        this.ticketRepo = ticketRepo;
    }

    // ✅ Generics
    public SearchResult<Event> getAllEvents() {
        List<Event> events = eventRepo.findAll();
        return new SearchResult<>(events);
    }

    // ✅ Generics
    public SearchResult<Ticket> getAllTickets() {
        List<Ticket> tickets = ticketRepo.findAll();
        return new SearchResult<>(tickets);
    }

    // ✅ TicketType  + Factory
    public void buyTicket(int eventId, int seatId, int customerId, String ticketCode, TicketType type) {

        Event event = eventRepo.findById(eventId);
        if (event == null) throw new RuntimeException("Event not found");

        if (event.isCancelled()) throw new EventCancelledException();

        Seat seat = seatRepo.findById(seatId);
        if (seat == null) throw new RuntimeException("Seat not found");

        if (seat.getEventId() != eventId) throw new RuntimeException("Seat does not belong to this event");

        if (seat.isReserved()) throw new SeatAlreadyBookedException();

        // reserve seat once
        seatRepo.reserve(seatId);

        // ✅ ticket          Factory
        Ticket ticket = TicketFactory.createTicket(type, eventId, seatId, customerId, ticketCode);

        // save ticket
        ticketRepo.save(ticket);
    }
}
