package edu.aitu.oop3.exceptions;

public class EventCancelledException extends RuntimeException {
    public EventCancelledException() {

        super("Event is cancelled")
        ;
    }
}
