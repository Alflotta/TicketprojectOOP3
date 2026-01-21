package edu.aitu.oop3.repositories.impl;
import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.entities.Customer;
import edu.aitu.oop3.repositories.CustomerRepository;

import java.sql.*;

public class CustomerRepositoryImpl implements CustomerRepository {

    @Override
    public Customer findByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setPassword(rs.getString("password"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

