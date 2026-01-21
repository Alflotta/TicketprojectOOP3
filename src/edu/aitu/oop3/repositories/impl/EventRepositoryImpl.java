package edu.aitu.oop3.repositories.impl;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.entities.Event;
import edu.aitu.oop3.repositories.EventRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    @Override
    public Event findById(int id) {
        String sql = "SELECT * FROM events WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setLocation(rs.getString("location"));
                e.setDate(rs.getTimestamp("date").toLocalDateTime());
                e.setCancelled(rs.getBoolean("cancelled"));
                return e;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setLocation(rs.getString("location"));
                e.setDate(rs.getTimestamp("date").toLocalDateTime());
                e.setCancelled(rs.getBoolean("cancelled"));
                events.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return events;
    }
}
