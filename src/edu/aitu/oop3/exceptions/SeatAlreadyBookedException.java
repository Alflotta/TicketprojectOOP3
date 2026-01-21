package edu.aitu.oop3.exceptions;

public class SeatAlreadyBookedException extends RuntimeException {
    public SeatAlreadyBookedException() {

        super("Seat is already booked");
    }
}