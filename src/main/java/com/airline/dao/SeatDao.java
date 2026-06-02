package com.airline.dao;

import com.airline.resource.Database; // Connection resource import kiya
import com.airline.model.Seat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDao {

    public List<Seat> getSeatsByFlight(int flightId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE flight_id = ?";

        try (Connection conn = Database.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, flightId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Timestamp holdExpiresTimestamp = rs.getTimestamp("hold_expires_at");
                seats.add(new Seat(
                    rs.getInt("seat_id"),
                    rs.getInt("flight_id"),
                    rs.getString("seat_number"),
                    rs.getString("seat_class"),
                    rs.getString("status"),
                    holdExpiresTimestamp != null ? holdExpiresTimestamp.toLocalDateTime() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public int addSeat(Seat seat) {
    	int rowsInserted = 0;
        String sql = "INSERT INTO seats (flight_id, seat_number, seat_class, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, seat.getFlightId());
            ps.setString(2, seat.getSeatNumber());
            ps.setString(3, seat.getSeatClass());
            ps.setString(4, seat.getStatus() != null ? seat.getStatus() : "AVAILABLE");

            rowsInserted = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsInserted;
    }

}