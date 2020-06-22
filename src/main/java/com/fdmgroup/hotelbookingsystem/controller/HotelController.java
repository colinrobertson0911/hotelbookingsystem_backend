package com.fdmgroup.hotelbookingsystem.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.fdmgroup.hotelbookingsystem.exceptions.HotelOwnerNotFoundException;
import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.hotelbookingsystem.exceptions.BookingNotFoundException;
import com.fdmgroup.hotelbookingsystem.exceptions.HotelNotFoundException;
import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.BookingService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.RoomService;

@RestController
@RequestMapping("/hotel")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class HotelController {

	Pageable firstPageWithTwoElements = PageRequest.of(0, 2);

	Pageable secondPageWithFiveElements = PageRequest.of(1, 5);
	
	@Autowired
	HotelService hotelService;

	@Autowired
	RoomService roomService;

	@GetMapping("/SearchByCity/{city}")
	public ResponseEntity<List<Hotel>> searchByCity(@PathVariable("city") String city) {
		List<Hotel> cityInDB = hotelService.findByCity(city,firstPageWithTwoElements);
		if (cityInDB.isEmpty()) {
			return new ResponseEntity<List<Hotel>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Hotel>>(cityInDB,HttpStatus.OK);
	}

	@GetMapping("/SeeHotel/{hotelName}")
	public Hotel verifyHotel(@PathVariable("hotelName") String hotelName) {
		return ((Optional<Hotel>) hotelService.retrieveOne(hotelName)).orElseThrow(()
				-> new HotelNotFoundException(hotelName));
	}

	@GetMapping("/SeeHotelById/{hotelId}")
	public Hotel getHotelById(@PathVariable("hotelId") int hotelId) {
		return ((Optional<Hotel>) hotelService.findById(hotelId)).orElseThrow(()
				-> new HotelNotFoundException(hotelId + ""));
	}


	@GetMapping("/SearchByRoomType/{roomType}")
	public ResponseEntity<List<Hotel>> searchByRoomType(@PathVariable("roomType") String roomType){
		List<Hotel> hotelsWithRT = hotelService.findByRoomType(roomType,firstPageWithTwoElements);
		if(hotelsWithRT.isEmpty()){
			return new ResponseEntity<List<Hotel>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Hotel>>(hotelsWithRT, HttpStatus.OK);
	}

	@GetMapping("/SearchByAvailability/{checkInDate},{checkOutDate}")
	public ResponseEntity<List<Hotel>> searchByAvailability(@PathVariable("checkInDate")@DateTimeFormat(pattern = "yyyy-MM-dd") String checkInDateString,
														   @PathVariable("checkOutDate")@DateTimeFormat(pattern = "yyyy-MM-dd") String checkOutDateString){
		LocalDate checkInDate = LocalDate.parse(checkInDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate checkOutDate = LocalDate.parse(checkOutDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		List<Hotel> hotelList = hotelService.findByAvailabilityWithSpecifiedDates(checkInDate, checkOutDate, firstPageWithTwoElements);
		if(hotelList.isEmpty()){
			return new ResponseEntity<List<Hotel>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Hotel>>(hotelList, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_VIEWER')")
	@GetMapping("/AllRooms")
	public ResponseEntity<List<Room>> allRooms(){
		return ResponseEntity.ok(roomService.findAll());
	}

}
