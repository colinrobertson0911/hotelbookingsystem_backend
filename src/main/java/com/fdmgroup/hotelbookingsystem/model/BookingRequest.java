package com.fdmgroup.hotelbookingsystem.model;

public class BookingRequest {

	private String hotel;
	private String checkInDate;
	private String checkOutDate;
	private String roomType;
	
	public BookingRequest(String hotel, String checkInDate, String checkOutDate, String roomType) {
		this.hotel = hotel;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomType = roomType;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckinDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Override
	public String toString() {
		return "BookingRequest [hotel=" + hotel + ", checkinDate=" + checkInDate + ", checkoutDate=" + checkOutDate
				+ ", roomType=" + roomType + "]";
	}
	
	
	
}
