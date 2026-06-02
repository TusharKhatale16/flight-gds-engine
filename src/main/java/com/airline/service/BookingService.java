package com.airline.service;

import com.airline.dao.BookingDao;
import com.airline.model.Booking;

public class BookingService {
    // BookingDao ka object banaya taaki database operations call kar sakein
    private BookingDao bookingDao = new BookingDao();

    /**
     * Step 1: User ke liye seat temporary hold/lock karna
     * Yeh check karega ki seat available hai ya nahi, aur hai toh use 5 min ke liye lock kar dega.
     */
    public boolean holdSeatForUser(int flightId, String seatNumber) {
        // Direct Dao ke reserveSeat method ko call kiya
        return bookingDao.reserveSeat(flightId, seatNumber);
    }

    /**
     * Step 2: Billing/Payment simulation ke baad ticket confirm karna
     * Yeh bookings table mein record daalega aur seat ko permanently 'BOOKED' mark karega.
     */
    public Booking completeTicketPurchase(int flightId, String seatNumber, String passengerName, double finalPrice) {
        // Yahan future mein aap payment gateway ka logic laga sakte hain
        // Abhi ke liye yeh direct data database mein save kar raha hai
        return bookingDao.saveBooking(flightId, seatNumber, passengerName, finalPrice);
    }
}