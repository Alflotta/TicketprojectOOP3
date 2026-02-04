package edu.aitu.oop3.entities;

public class Ticket {
    private int id;
    private int eventId;
    private int seatId;
    private int customerId;
    private String ticketCode;

    public Ticket() {}
    private String ticketType;
    private double price;
    public String getTicketType() { return ticketType; }
    public void setTicketType(String ticketType) { this.ticketType = ticketType; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }


    public Ticket(int eventId, int seatId, int customerId, String ticketCode) {
        this.eventId = eventId;
        this.seatId = seatId;
        this.customerId = customerId;
        this.ticketCode = ticketCode;
    }

    public Ticket(int id, int eventId, int seatId, int customerId, String ticketCode) {
        this.id = id;
        this.eventId = eventId;
        this.seatId = seatId;
        this.customerId = customerId;
        this.ticketCode = ticketCode;
    }

    public int getId() { return id; }
    public int getEventId() { return eventId; }
    public int getSeatId() { return seatId; }
    public int getCustomerId() { return customerId; }
    public String getTicketCode() { return ticketCode; }

    public void setId(int id) { this.id = id; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public void setSeatId(int seatId) { this.seatId = seatId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }

    @Override
    public String toString() {
        return "Ticket{id=" + id + ", eventId=" + eventId + ", seatId=" + seatId + ", customerId=" + customerId + ", ticketCode='" + ticketCode + "'}";
    }
}
