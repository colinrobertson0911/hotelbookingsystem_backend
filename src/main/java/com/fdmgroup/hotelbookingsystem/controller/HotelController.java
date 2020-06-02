package com.fdmgroup.hotelbookingsystem.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/hotelbookingsystem/hotel")
@CrossOrigin(origins = "http://localhost:4200")
public class HotelController {

	public static final String SESSION_ATTRIBUTE_BOOKINGID = "BOOKINGID";

	@Autowired
	HotelService hotelService;

	@Autowired
	RoomService roomService;

	@Autowired
	BookingService bookingService;


//	@GetMapping("/SearchByCity/{city}")
//	public ResponseEntity<List<Hotel>> searchByCity(@PathVariable("city") String city) {
//		return ResponseEntity.ok(hotelService.findByCity(city));
//	}

	@GetMapping("/SearchByCity/{city}")
	public ResponseEntity<HttpStatus> searchByCity(@PathVariable("city") String city) {
		List<Hotel> cityInDB = hotelService.findByCity(city);
		if (cityInDB.isEmpty()) {
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(HttpStatus.FOUND);
	}

	@GetMapping("/SeeHotel/{hotelId}")
	public Hotel verifyHotel(@PathVariable("hotelId") long hotelId) {
		return ((Optional<Hotel>) hotelService.retrieveOne(hotelId)).orElseThrow(()
				-> new HotelNotFoundException(hotelId));
	}

	@PostMapping("/BookingSubmit")
	public ResponseEntity <HttpStatus> bookingSubmit(@RequestBody Bookings booking) {
		try {
			bookingService.save(booking);
		} catch(DataIntegrityViolationException e){
			return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@GetMapping("/BookingConfirmation/{bookingId}")
	public Bookings bookingConfirmationSubmit(@PathVariable("bookingId") Long bookingId) {
		return ((Optional<Bookings>) bookingService.retrieveOne(bookingId)).orElseThrow(()
				-> new BookingNotFoundException(bookingId));
	}

	
	@GetMapping("/SearchByRoomType/{roomType}")
	public ResponseEntity<HttpStatus> searchByRoomType(@PathVariable("roomType") String roomType){
		List<Hotel> hotelsWithRT = hotelService.findByRoomType(roomType);
		if(hotelsWithRT.isEmpty()){
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(HttpStatus.FOUND);
	}

	@GetMapping("/SearchByAvailability/{checkInDate}, {checkOutDate}")
	public ResponseEntity<HttpStatus> searchByAvailability(@PathVariable("checkInDate")String checkInDateString,
														   @PathVariable("checkOutDate")String checkOutDateString){
		LocalDate checkInDate = LocalDate.parse(checkInDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate checkOutDate = LocalDate.parse(checkOutDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		List<Hotel> hotelList = hotelService.findByAvailabilityAndVerifiedWithSpecifiedDates(checkInDate, checkOutDate);
		if(hotelList.isEmpty()){
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(HttpStatus.FOUND);
	}

//	@PostMapping("SearchByAvailability")
//	public ModelAndView searchbyAvailability(
//			@RequestParam(name = "checkInDate", defaultValue = "") String checkInDateString,
//			@RequestParam(name = "checkOutDate", defaultValue = "") String checkOutDateString, ModelMap model) {
//		LocalDate checkInDate = LocalDate.parse(checkInDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//		LocalDate checkOutDate = LocalDate.parse(checkOutDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//		List<Hotel> hotelList = hotelService.findByAvailabilityAndVerifiedWithSpecifiedDates(checkInDate, checkOutDate);
//		ModelAndView modelAndView = new ModelAndView();
//		if (hotelList.isEmpty()) {
//			modelAndView.setViewName("mainScreen.jsp");
//			modelAndView.addObject("errorAvailabilityMessage", "No Rooms available");
//			modelAndView.addObject("visabilityMessage", "All Hotels");
//			modelAndView.addObject("hotel", hotelService.findByVerifiedEqualsTrue());
//			modelAndView.addObject("allRooms", roomService.findAll());
//			return modelAndView;
//		}
//
//		modelAndView.setViewName("mainScreen.jsp");
//		modelAndView.addObject("visabilityMessage", "Hotels available between " + checkInDate + " and " + checkOutDate);
//		modelAndView.addObject("hotel", hotelList);
//		modelAndView.addObject("allRooms", roomService.findAll());
//		return modelAndView;
//
//	}

}
