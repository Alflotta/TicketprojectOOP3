package edu.aitu.oop3.repositories.impl;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.entities.Ticket;
import edu.aitu.oop3.repositories.TicketRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketRepositoryImpl implements TicketRepository {
    @Override
    public void save(Ticket ticket) {
        String sql = "INSERT INTO tickets(event_id, seat_id, customer_id, ticket_code, ticket_type, price) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, ticket.getEventId());
            st.setInt(2, ticket.getSeatId());
            st.setInt(3, ticket.getCustomerId());
            st.setString(4, ticket.getTicketCode());
            st.setString(5, ticket.getTicketType());
            st.setDouble(6, ticket.getPrice());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save ticket: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Ticket> findByCustomerId(int customerId) {
        List<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT id, event_id, seat_id, customer_id, ticket_code, ticket_type, price " +
                "FROM tickets WHERE customer_id = ? ORDER BY id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, customerId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Ticket t = new Ticket();
                    t.setId(rs.getInt("id"));
                    t.setEventId(rs.getInt("event_id"));
                    t.setSeatId(rs.getInt("seat_id"));
                    t.setCustomerId(rs.getInt("customer_id"));
                    t.setTicketCode(rs.getString("ticket_code"));
                    t.setTicketType(rs.getString("ticket_type"));
                    t.setPrice(rs.getDouble("price"));
                    tickets.add(t);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to load tickets: " + e.getMessage(), e);
        }

        return tickets;
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT id, event_id, seat_id, customer_id, ticket_code, ticket_type, price FROM tickets ORDER BY id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getInt("id"));
                t.setEventId(rs.getInt("event_id"));
                t.setSeatId(rs.getInt("seat_id"));
                t.setCustomerId(rs.getInt("customer_id"));
                t.setTicketCode(rs.getString("ticket_code"));
                t.setTicketType(rs.getString("ticket_type"));
                t.setPrice(rs.getDouble("price"));
                tickets.add(t);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tickets;
    }


}
