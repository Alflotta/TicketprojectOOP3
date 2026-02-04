package edu.aitu.oop3.builders;

import edu.aitu.oop3.entities.Event;
import java.time.LocalDateTime;

public class EventBuilder {
    private final Event event = new Event();

    public EventBuilder title(String title) {
        event.setName(title);
        return this;
    }

    public EventBuilder venue(String venue) {
        event.setLocation(venue);
        return this;
    }

    public EventBuilder schedule(LocalDateTime dateTime) {
        event.setDate(dateTime);
        return this;
    }

    public EventBuilder cancelled(boolean cancelled) {
        event.setCancelled(cancelled);
        return this;
    }

    public Event build() {
        return event;
    }
}

