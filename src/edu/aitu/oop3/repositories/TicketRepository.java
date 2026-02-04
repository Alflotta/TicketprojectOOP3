package edu.aitu.oop3.repositories;
import edu.aitu.oop3.entities.Ticket;
import java.util.List;
public interface TicketRepository {
    void save(Ticket ticket);
    List<Ticket> findAll();
    List<Ticket> findByCustomerId(int customerId);

}

