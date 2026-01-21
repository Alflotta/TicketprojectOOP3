package edu.aitu.oop3.repositories.impl;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.entities.Ticket;
import edu.aitu.oop3.repositories.TicketRepository;

import java.sql.*;

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
}
