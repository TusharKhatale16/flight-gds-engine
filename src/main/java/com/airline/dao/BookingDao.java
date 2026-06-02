package com.airline.dao;

import com.airline.resource.Database; // Aapka connection resource wrapper
import com.airline.model.Booking;
import java.sql.*;
import java.time.LocalDateTime;

public class BookingDao {

    public boolean reserveSeat(int flightId, String seatNumber) {
        String selectSql = "SELECT status, hold_expires_at FROM seats WHERE flight_id = ? AND seat_number = ? FOR UPDATE";
        String updateSql = "UPDATE seats SET status = 'HELD', hold_expires_at = ? WHERE flight_id = ? AND seat_number = ?";
        
        try (Connection conn = Database.createConnection()) {
            if (conn == null) return false;
            conn.setAutoCommit(false); 
            
            try (PreparedStatement psSel = conn.prepareStatement(selectSql)) {
                psSel.setInt(1, flightId);
                psSel.setString(2, seatNumber);
                ResultSet rs = psSel.executeQuery();
                
                if (rs.next()) {
                    String status = rs.getString("status");
                    Timestamp expiresAt = rs.getTimestamp("hold_expires_at");
                    
                    if (status.equals("AVAILABLE") || (status.equals("HELD") && expiresAt != null && expiresAt.toLocalDateTime().isBefore(LocalDateTime.now()))) {
                        
                        try (PreparedStatement psUp = conn.prepareStatement(updateSql)) {
                            LocalDateTime holdTime = LocalDateTime.now().plusMinutes(5); // 5 minute lock window
                            psUp.setTimestamp(1, Timestamp.valueOf(holdTime));
                            psUp.setInt(2, flightId);
                            psUp.setString(3, seatNumber);
                            psUp.executeUpdate();
                            
                            conn.commit(); 
                            return true;
                        }
                    }
                }
                conn.rollback();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Booking saveBooking(int flightId, String seatNumber, String passengerName, double price) {
        String getSeatIdSql = "SELECT seat_id FROM seats WHERE flight_id = ? AND seat_number = ?";
        String insertBookingSql = "INSERT INTO bookings (flight_id, seat_id, passenger_name, final_price, booking_time) VALUES (?, ?, ?, ?, ?)";
        String confirmSeatSql = "UPDATE seats SET status = 'BOOKED', hold_expires_at = NULL WHERE seat_id = ?";

        try (Connection conn = Database.createConnection()) {
            if (conn == null) return null;
            conn.setAutoCommit(false); // Transaction switch ON
            
            try (PreparedStatement psSeat = conn.prepareStatement(getSeatIdSql)) {
                psSeat.setInt(1, flightId);
                psSeat.setString(2, seatNumber);
                ResultSet rs = psSeat.executeQuery();
                
                if (rs.next()) {
                    int seatId = rs.getInt("seat_id");
                    
                    // 1. Insert ticket data into bookings table
                    try (PreparedStatement psBook = conn.prepareStatement(insertBookingSql, Statement.RETURN_GENERATED_KEYS)) {
                        psBook.setInt(1, flightId);
                        psBook.setInt(2, seatId);
                        psBook.setString(3, passengerName);
                        psBook.setDouble(4, price);
                        psBook.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                        psBook.executeUpdate();
                        
                        ResultSet generatedKeys = psBook.getGeneratedKeys();
                        int bookingId = 0;
                        if (generatedKeys.next()) {
                            bookingId = generatedKeys.getInt(1);
                        }

                        // 2. Permanently lock the seat status
                        try (PreparedStatement psConf = conn.prepareStatement(confirmSeatSql)) {
                            psConf.setInt(1, seatId);
                            psConf.executeUpdate();
                        }
                        
                        conn.commit(); // Pure changes database mein save ho gaye
                        return new Booking(bookingId, flightId, seatId, passengerName, price, LocalDateTime.now());
                    }
                }
                conn.rollback();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}