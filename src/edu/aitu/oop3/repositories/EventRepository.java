package edu.aitu.oop3.repositories;

import edu.aitu.oop3.entities.Event;
import java.util.List;

public interface EventRepository {
    Event findById(int id);
    List<Event> findAll();
}
