package edu.aitu.oop3.repositories;

import edu.aitu.oop3.entities.Seat;
import java.util.List;

public interface SeatRepository {
    Seat findById(int id);
    void reserve(int seatId);
    List<Seat> findByEventId(int eventId);
}

