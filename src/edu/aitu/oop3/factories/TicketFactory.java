package edu.aitu.oop3.factories;

import edu.aitu.oop3.entities.*;

public class TicketFactory {

    public static Ticket createTicket(TicketType type, int eventId, int seatId, int customerId, String code) {
        return switch (type) {
            case VIP -> new VIPTicket(eventId, seatId, customerId, code);
            case STUDENT -> new StudentTicket(eventId, seatId, customerId, code);
            default -> new StandardTicket(eventId, seatId, customerId, code);
        };
    }
}
