package edu.aitu.oop3.repositories.impl;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.entities.Ticket;
import edu.aitu.oop3.repositories.TicketRepository;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class TicketRepositoryImpl implements TicketRepository {

    @Override
    public void save(Ticket ticket) {
        String sql = "INSERT INTO tickets(event_id, seat_id, customer_id, ticket_code) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, ticket.getEventId());
            st.setInt(2, ticket.getSeatId());
            st.setInt(3, ticket.getCustomerId());
            st.setString(4, ticket.getTicketCode());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getInt("id"));
                t.setEventId(rs.getInt("event_id"));
                t.setSeatId(rs.getInt("seat_id"));
                t.setCustomerId(rs.getInt("customer_id"));
                t.setTicketCode(rs.getString("ticket_code"));
                tickets.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

}