package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.exceptions.HotelNotFoundException;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/hotel")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class HotelController {

	@Autowired
	HotelService hotelService;

	@Autowired
	RoomService roomService;

	@GetMapping("/SearchByCity/{city}")
	public ResponseEntity<List<Hotel>> searchByCity(@PathVariable("city") String city, @RequestParam("page")int page, @RequestParam("size")int size) {
		List<Hotel> cityInDB = hotelService.findByCity(city, page, size);
		if (cityInDB.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(cityInDB, HttpStatus.OK);
	}

	@GetMapping("/SeeHotel/{hotelName}")
	public Hotel verifyHotel(@PathVariable("hotelName") String hotelName) {
		return hotelService.retrieveOne(hotelName).orElseThrow(()
				-> new HotelNotFoundException(hotelName));
	}

	@GetMapping("/SeeHotelById/{hotelId}")
	public Hotel getHotelById(@PathVariable("hotelId") int hotelId) {
		return hotelService.findById(hotelId).orElseThrow(()
				-> new HotelNotFoundException(hotelId + ""));
	}

	@GetMapping("/SearchByRoomType/{roomType}")
	public ResponseEntity<List<Hotel>> searchByRoomType(@PathVariable("roomType") String roomType,@RequestParam("page")int page, @RequestParam("size")int size){
		List<Hotel> hotelsWithRT = hotelService.findByRoomType(roomType,page,size);
		if(hotelsWithRT.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(hotelsWithRT, HttpStatus.OK);
	}

	@GetMapping("/SearchByAvailability/{checkInDate},{checkOutDate}")
	public ResponseEntity<List<Hotel>> searchByAvailability(@PathVariable("checkInDate")@DateTimeFormat(pattern = "yyyy-MM-dd") String checkInDateString,
														   @PathVariable("checkOutDate")@DateTimeFormat(pattern = "yyyy-MM-dd") String checkOutDateString
														   ){
		LocalDate checkInDate = LocalDate.parse(checkInDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate checkOutDate = LocalDate.parse(checkOutDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		List<Hotel> hotelList = hotelService.findByAvailabilityWithSpecifiedDates(checkInDate, checkOutDate);
		if(hotelList.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(hotelList, HttpStatus.OK);
	}

	@GetMapping("/AllRooms")
	public ResponseEntity<List<Room>> allRooms(){
		return ResponseEntity.ok(roomService.findAll());
	}

}
