package com.fdmgroup.hotelbookingsystem.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.format.annotation.DateTimeFormat;

import com.fdmgroup.hotelbookingsystem.controller.utility.FormatWithLocale;

@Entity
public class Bookings {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_gen")
	@SequenceGenerator(name = "booking_gen", sequenceName = "BOOKING_SEQ", allocationSize = 1)
	private long bookingId;

	@Column
	private String roomType;

	@Column
	private String hotel;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkInDate;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkOutDate;

	@Column
	private BigDecimal roomPrice;

	@Column
	private BigDecimal extrasPrice;

	@Column
	private BigDecimal totalPrice;

	@Enumerated(EnumType.STRING)
	private Extras extras;

	public Bookings() {
		super();
	}

	public Bookings(String roomType, String hotel, LocalDate checkInDate, LocalDate checkOutDate, BigDecimal roomPrice,
			BigDecimal extrasPrice, BigDecimal totalPrice, Extras extras) {
		super();
		this.roomType = roomType;
		this.hotel = hotel;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomPrice = roomPrice;
		this.extrasPrice = extrasPrice;
		this.totalPrice = totalPrice;
		this.extras = extras;
	}

	public String getCheckInDateFormatted() {
		return this.getCheckInDate().format(FormatWithLocale.DATE_FORMATTER);
	}

	public String getCheckOutDateFormatted() {
		return this.getCheckOutDate().format(FormatWithLocale.DATE_FORMATTER);
	}

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public BigDecimal getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(BigDecimal roomPrice) {
		this.roomPrice = roomPrice;
	}

	public BigDecimal getExtrasPrice() {
		return extrasPrice;
	}

	public void setExtrasPrice(BigDecimal extrasPrice) {
		this.extrasPrice = extrasPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Extras getExtras() {
		return extras;
	}

	public void setExtras(Extras extras) {
		this.extras = extras;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (bookingId ^ (bookingId >>> 32));
		result = prime * result + ((checkInDate == null) ? 0 : checkInDate.hashCode());
		result = prime * result + ((checkOutDate == null) ? 0 : checkOutDate.hashCode());
		result = prime * result + ((extras == null) ? 0 : extras.hashCode());
		result = prime * result + ((extrasPrice == null) ? 0 : extrasPrice.hashCode());
		result = prime * result + ((hotel == null) ? 0 : hotel.hashCode());
		result = prime * result + ((roomPrice == null) ? 0 : roomPrice.hashCode());
		result = prime * result + ((roomType == null) ? 0 : roomType.hashCode());
		result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bookings other = (Bookings) obj;
		if (bookingId != other.bookingId)
			return false;
		if (checkInDate == null) {
			if (other.checkInDate != null)
				return false;
		} else if (!checkInDate.equals(other.checkInDate))
			return false;
		if (checkOutDate == null) {
			if (other.checkOutDate != null)
				return false;
		} else if (!checkOutDate.equals(other.checkOutDate))
			return false;
		if (extras != other.extras)
			return false;
		if (extrasPrice == null) {
			if (other.extrasPrice != null)
				return false;
		} else if (!extrasPrice.equals(other.extrasPrice))
			return false;
		if (hotel == null) {
			if (other.hotel != null)
				return false;
		} else if (!hotel.equals(other.hotel))
			return false;
		if (roomPrice == null) {
			if (other.roomPrice != null)
				return false;
		} else if (!roomPrice.equals(other.roomPrice))
			return false;
		if (roomType == null) {
			if (other.roomType != null)
				return false;
		} else if (!roomType.equals(other.roomType))
			return false;
		if (totalPrice == null) {
			if (other.totalPrice != null)
				return false;
		} else if (!totalPrice.equals(other.totalPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bookings [bookingId=" + bookingId + ", roomType=" + roomType + ", hotel=" + hotel + ", checkInDate="
				+ checkInDate + ", checkOutDate=" + checkOutDate + ", roomPrice=" + roomPrice + ", extrasPrice="
				+ extrasPrice + ", totalPrice=" + totalPrice + ", extras=" + extras + "]";
	}

}
