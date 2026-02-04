package edu.aitu.oop3.repositories.impl;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.entities.Seat;
import edu.aitu.oop3.repositories.SeatRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatRepositoryImpl implements SeatRepository {
    @Override
    public Seat findById(int id) {
        String sql = "SELECT id, event_id, seat_number, reserved FROM seats WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Seat s = new Seat();
                    s.setId(rs.getInt("id"));
                    s.setEventId(rs.getInt("event_id"));
                    s.setSeatNumber(rs.getString("seat_number"));
                    s.setReserved(rs.getBoolean("reserved"));
                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void reserve(int seatId) {
        String sql = "UPDATE seats SET reserved = true WHERE id = ? AND reserved = false";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, seatId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Seat> findByEventId(int eventId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT id, event_id, seat_number, reserved FROM seats WHERE event_id = ? ORDER BY seat_number";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, eventId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Seat s = new Seat();
                    s.setId(rs.getInt("id"));
                    s.setEventId(rs.getInt("event_id"));
                    s.setSeatNumber(rs.getString("seat_number"));
                    s.setReserved(rs.getBoolean("reserved"));
                    seats.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }
}

