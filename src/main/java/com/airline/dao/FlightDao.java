package com.airline.dao;

import com.airline.resource.Database; // Apka JDBC connection wrapper import kiya
import com.airline.model.Flight;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDao {

    // Method 1: Source aur Destination ke hisab se saari matching flights dhoodna
    public List<Flight> getFlightsByRoute(String source, String destination) {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM flights WHERE source = ? AND destination = ?";
        
        try (Connection conn = Database.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, source);
            ps.setString(2, destination);
            ResultSet rs = ps.executeQuery();
            
            // while loop lagaya hai taaki saari matching flights list mein add hon
            while (rs.next()) {
                flights.add(new Flight(
                    rs.getInt("flight_id"),
                    rs.getString("flight_number"),
                    rs.getString("source"),
                    rs.getString("destination"),
                    rs.getTimestamp("departure_time").toLocalDateTime(),
                    rs.getDouble("base_price"),
                    rs.getDouble("current_price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    // Method 2: Kisi flight mein kul (total) kitni seats hain unka count nikalna
    public int getTotalSeatsCount(int flightId) {
        String sql = "SELECT COUNT(*) FROM seats WHERE flight_id = ?";
        try (Connection conn = Database.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, flightId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Method 3: Flight mein kitni seats permanently BOOKED ho chuki hain unka count nikalna
    public int getBookedSeatsCount(int flightId) {
        String sql = "SELECT COUNT(*) FROM seats WHERE flight_id = ? AND status = 'BOOKED'";
        try (Connection conn = Database.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, flightId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Method 4: FlightService jab surge price nikalega, toh use database mein update karna
    public void updateFlightPrice(int flightId, double newPrice) {
        String sql = "UPDATE flights SET current_price = ? WHERE flight_id = ?";
        try (Connection conn = Database.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDouble(1, newPrice);
            ps.setInt(2, flightId);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}