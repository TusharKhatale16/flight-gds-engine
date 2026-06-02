package com.airline;

import com.airline.model.Flight;
import com.airline.model.Booking;
import com.airline.service.FlightService;
import com.airline.service.BookingService;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String args[]) {
        FlightService flightService = new FlightService();
        BookingService bookingService = new BookingService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("✈️ === WELCOME TO AIRLINE GDS RESERVATION SYSTEM ===");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Search Flights (Routes & Live Prices)");
            System.out.println("2. Book a Flight Ticket (With Seat Locking)");
            System.out.println("3. Exit Application");
            System.out.println("👉 Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Scanner buffer clear karne ke liye

            if (choice == 1) {
                // ------------------ FLOW 1: SEARCH FLIGHTS ------------------
                System.out.print("\nEnter Source Airport (e.g., BOM): ");
                String source = scanner.nextLine().toUpperCase();
                System.out.print("Enter Destination Airport (e.g., DEL): ");
                String destination = scanner.nextLine().toUpperCase();

                System.out.println("\n🔍 Searching for flights from " + source + " to " + destination + "...");
                List<Flight> flights = flightService.searchFlights(source, destination);

                if (flights.isEmpty()) {
                    System.out.println("❌ No flights found for this route.");
                } else {
                    System.out.println("\n🛫 Available Flights (Prices include live surge multiplier):");
                    System.out.println("----------------------------------------------------------------------");
                    for (Flight f : flights) {
                        System.out.println("ID: " + f.getFlightId() + 
                                           " | Number: " + f.getFlightNumber() + 
                                           " | Price: ₹" + f.getCurrentPrice() + 
                                           " | Time: " + f.getDepartureTime());
                    }
                    System.out.println("----------------------------------------------------------------------");
                }

            } else if (choice == 2) {
                // ------------------ FLOW 2: BOOK A TICKET ------------------
                System.out.print("\nEnter Flight ID to book: ");
                int flightId = scanner.nextInt();
                scanner.nextLine(); // Buffer clear
                
                System.out.print("Enter Seat Number (e.g., 1A, 10B): ");
                String seatNumber = scanner.nextLine().toUpperCase();
                
                System.out.print("Enter Passenger Name: ");
                String passengerName = scanner.nextLine();

                System.out.println("\n🔒 Attempting to lock seat " + seatNumber + " for 5 minutes...");
                
                // Step A: Seat ko temporary hold par daalna (Concurrency Check)
                boolean isHeld = bookingService.holdSeatForUser(flightId, seatNumber);

                if (isHeld) {
                    System.out.println("🎉 Success! Seat " + seatNumber + " is temporarily LOCKED for you.");
                    System.out.println("💳 Simulating Payment Process... Please wait...");
                    
                    // Testing ke liye hum flight list se price nikal rahe hain ya ek static dynamic value process kar rahe hain
                    // Real flow mein hum user se confirm karwate hain
                    System.out.print("Proceed to confirm booking? (YES/NO): ");
                    String confirmation = scanner.nextLine().toUpperCase();

                    if (confirmation.equals("YES")) {
                        // Maan lete hain user ne booking amount pay kiya, hum ₹5000 base ya current estimate use kar rahe hain
                        // Hamara service layer database se automatically details match kar lega
                        double samplePrice = 5000.0; 

                        // Step B: Final booking save karna aur seat permanently BOOKED karna
                        Booking finalBooking = bookingService.completeTicketPurchase(flightId, seatNumber, passengerName, samplePrice);
                        
                        if (finalBooking != null) {
                            System.out.println("\n✅ TICKET BOOKED SUCCESSFULLY!");
                            System.out.println("----------------------------------------");
                            System.out.println("Ticket ID     : " + finalBooking.getBookingId());
                            System.out.println("Passenger Name: " + finalBooking.getPassengerName());
                            System.out.println("Seat Allocated: " + seatNumber);
                            System.out.println("Amount Paid   : ₹" + finalBooking.getFinalPrice());
                            System.out.println("----------------------------------------");
                        } else {
                            System.out.println("❌ Booking failed during database transaction.");
                        }
                    } else {
                        System.out.println("⚠️ Booking cancelled by user. Seat lock will expire automatically.");
                    }
                } else {
                    System.out.println("❌ Cannot book! Seat " + seatNumber + " is either ALREADY BOOKED or currently HELD by someone else.");
                }

            } else if (choice == 3) {
                System.out.println("👋 Thank you for using Airline GDS. Goodbye!");
                break;
            } else {
                System.out.println("❌ Invalid option! Please select 1, 2, or 3.");
            }
        }
        scanner.close();
    }
}