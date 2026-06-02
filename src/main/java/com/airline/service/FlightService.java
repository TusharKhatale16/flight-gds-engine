package com.airline.service;

import com.airline.dao.FlightDao;
import com.airline.model.Flight;
import java.util.List;

public class FlightService {
    
    private FlightDao flightDao = new FlightDao();

    /**
     * Route ke hisab se flights search karna aur unpar Dynamic Pricing apply karna
     */
    public List<Flight> searchFlights(String source, String destination) {
        // 1. Database se saari matching flights nikalen
        List<Flight> flights = flightDao.getFlightsByRoute(source, destination);

        // 2. Har flight par Dynamic Pricing ka rule lagayen
        for (Flight flight : flights) {
            int totalSeats = flightDao.getTotalSeatsCount(flight.getFlightId());
            int bookedSeats = flightDao.getBookedSeatsCount(flight.getFlightId());

            // Default current price ko base price par reset rakhein agar flight khali hai
            double updatedPrice = flight.getBasePrice();

            if (totalSeats > 0) {
                // Occupancy percentage nikalen (Kitne % seats bhar gayi hain)
                double occupancyRate = ((double) bookedSeats / totalSeats) * 100;

                // Algorithm Rule 1: Agar 80% se zyada seats full hain -> 50% Surge Price
                if (occupancyRate >= 80.0) {
                    updatedPrice = flight.getBasePrice() * 1.50;
                } 
                // Algorithm Rule 2: Agar 50% se zyada seats full hain -> 20% Surge Price
                else if (occupancyRate >= 50.0) {
                    updatedPrice = flight.getBasePrice() * 1.20;
                }
            }

            // 3. Agar price badha hai, toh use object aur database dono mein update karein
            if (updatedPrice != flight.getCurrentPrice()) {
                flight.setCurrentPrice(updatedPrice);
                flightDao.updateFlightPrice(flight.getFlightId(), updatedPrice);
            }
        }

        return flights;
    }
}