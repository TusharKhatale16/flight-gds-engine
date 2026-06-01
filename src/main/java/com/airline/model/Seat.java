package com.airline.model;

import java.time.LocalDateTime;

public class Seat {

	private int seatId;
	private int flightId;
	private String seatNumber;
	private String seatClass;
	private String status;
	private LocalDateTime holdExpiresAt;
	
	
	
	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getHoldExpiresAt() {
		return holdExpiresAt;
	}

	public void setHoldExpiresAt(LocalDateTime holdExpiresAt) {
		this.holdExpiresAt = holdExpiresAt;
	}

	public Seat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seat(int seatId, int flightId, String seatNumber, String seatClass, String status,
			LocalDateTime holdExpiresAt) {
		super();
		this.seatId = seatId;
		this.flightId = flightId;
		this.seatNumber = seatNumber;
		this.seatClass = seatClass;
		this.status = status;
		this.holdExpiresAt = holdExpiresAt;
	}

	@Override
	public String toString() {
		return "Seat [seatId=" + seatId + ", flightId=" + flightId + ", seatNumber=" + seatNumber + ", seatClass="
				+ seatClass + ", status=" + status + ", holdExpiresAt=" + holdExpiresAt + "]";
	}
	
	
}
