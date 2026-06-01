package com.airline.model;

import java.time.LocalDateTime;

public class Booking {

	private int bookingId;
    private int flightId;
    private int seatId;
    private String passengerName;
    private double finalPrice;
    private LocalDateTime bookingTime;
    
	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Booking(int bookingId, int flightId, int seatId, String passengerName, double finalPrice,
			LocalDateTime bookingTime) {
		super();
		this.bookingId = bookingId;
		this.flightId = flightId;
		this.seatId = seatId;
		this.passengerName = passengerName;
		this.finalPrice = finalPrice;
		this.bookingTime = bookingTime;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", flightId=" + flightId + ", seatId=" + seatId + ", passengerName="
				+ passengerName + ", finalPrice=" + finalPrice + ", bookingTime=" + bookingTime + "]";
	}
	
	 
    
}
