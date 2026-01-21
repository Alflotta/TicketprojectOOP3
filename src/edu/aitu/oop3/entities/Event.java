package edu.aitu.oop3.entities;

import java.time.LocalDateTime;

public class Event {
    private int id;
    private String name;
    private String location;
    private LocalDateTime date;
    private boolean cancelled;

    public Event() {}

    public Event(String name, String location, LocalDateTime date, boolean cancelled) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.cancelled = cancelled;
    }

    public Event(int id, String name, String location, LocalDateTime date, boolean cancelled) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.cancelled = cancelled;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public LocalDateTime getDate() { return date; }
    public boolean isCancelled() { return cancelled; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

    @Override
    public String toString() {
        return "Event{id=" + id + ", name='" + name + "', location='" + location + "', date=" + date + ", cancelled=" + cancelled + "}";
    }
}
