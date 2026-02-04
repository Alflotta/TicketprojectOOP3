package edu.aitu.oop3.services;
import edu.aitu.oop3.entities.Seat;
import edu.aitu.oop3.exceptions.SeatAlreadyBookedException;
import edu.aitu.oop3.repositories.SeatRepository;

public class SeatAllocationService {

    private final SeatRepository seatRepository;
    public SeatAllocationService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void reserveSeat(int seatId) {
        Seat seat = seatRepository.findById(seatId);

        if (seat == null) {
            throw new RuntimeException("Seat not found");
        }
        if (seat.isReserved()) {
            throw new SeatAlreadyBookedException();
        }

        seatRepository.reserve(seatId);
    }
}

