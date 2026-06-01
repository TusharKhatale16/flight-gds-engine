package com.airline.model;

import java.time.LocalDateTime;

public class Flight {

	private int flightId;
	private String flightNumber;
	private String source;
	private String destination;
	private LocalDateTime departureTime;
	private double basePrice;
	private double currentPrice;

	public Flight() {
		super();
	}

	public Flight(int flightId, String flightNumber, String source, String destination, LocalDateTime departureTime,
			double basePrice, double currentPrice) {
		super();
		this.flightId = flightId;
		this.flightNumber = flightNumber;
		this.source = source;
		this.destination = destination;
		this.departureTime = departureTime;
		this.basePrice = basePrice;
		this.currentPrice = currentPrice;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	@Override
	public String toString() {
		return "Flight [flightId=" + flightId + ", flightNumber=" + flightNumber + ", source=" + source
				+ ", destination=" + destination + ", departureTime=" + departureTime + ", basePrice=" + basePrice
				+ ", currentPrice=" + currentPrice + "]";
	}

}
