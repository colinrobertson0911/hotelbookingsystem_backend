package com.fdmgroup.hotelbookingsystem.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

import com.fdmgroup.hotelbookingsystem.exceptions.HotelCityNotFoundException;
import com.fdmgroup.hotelbookingsystem.exceptions.HotelNotFoundException;
import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Extras;
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


	@GetMapping("/SearchByCity/{city}")
	public ResponseEntity<List<Hotel>> searchByCity(@PathVariable("city") String city) {
		return ResponseEntity.ok(hotelService.findByCity(city));
	}

//	@GetMapping("/SearchByCity/{city}")
//	public ResponseEntity <HttpStatus> searchByCity(@PathVariable("city") String city) {
//		try {
//			hotelService.findByCity(city);
//		}catch (DataIntegrityViolationException ex) {
//			return new ResponseEntity<HttpStatus> (HttpStatus.NOT_FOUND);
//		}
//		return ResponseEntity.ok(HttpStatus.FOUND);
//	}

	@GetMapping("/SeeHotel/{hotelId}")
	public Hotel verifyHotel(@PathVariable("hotelId") long hotelId) {
		return ((Optional<Hotel>) hotelService.retrieveOne(hotelId)).orElseThrow(()
				-> new HotelNotFoundException(hotelId));
	}

//	@GetMapping("bookingPage")
//	public ModelAndView bookingPage(ModelMap model, @RequestParam("hotelId") long hotelId,
//			@RequestParam("roomId") long roomId) {
//		ModelAndView modelAndView = new ModelAndView();
//		Hotel hotel = hotelService.retrieveOne(hotelId).get();
//		Room room = roomService.retrieveOne(roomId);
//		modelAndView.setViewName("WEB-INF/bookingPage.jsp");
//		modelAndView.addObject("hotel", hotel);
//		modelAndView.addObject("room", room);
//		modelAndView.addObject("bookings", new Bookings());
//		if (hotel.isAirportTransfers() == true) {
//			modelAndView.addObject("Extras", EnumSet.allOf(Extras.class));
//		} else {
//			modelAndView.addObject("Extras", EnumSet.of(Extras.NO_EXTRAS));
//		}
//		return modelAndView;
//	}

	@PostMapping("/BookingSubmit")
	public ResponseEntity <HttpStatus> bookingSubmit(@RequestBody Bookings booking) {
		try {
			bookingService.save(booking);
		} catch(DataIntegrityViolationException e){
			return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@PostMapping("BookingConfirmationSubmit")
	public ModelAndView bookingConfirmationSubmit(@ModelAttribute("bookings") Bookings bookings, ModelMap model,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Object idFromSession = session.getAttribute("BOOKINGID");
		String bookingIdString = idFromSession.toString();
		Long bookingId = Long.parseLong(bookingIdString);
		Bookings bookingFromDataBase = bookingService.retrieveOne(bookingId);

		String hotelName = bookingFromDataBase.getHotel();
		Hotel hotel = hotelService.findByHotelName(hotelName);
		hotel.getBookings().add(bookingFromDataBase);
		hotelService.save(hotel);

		modelAndView.setViewName("mainScreen.jsp");
		modelAndView.addObject("ownerMessage", "Booking Confirmed");
		modelAndView.addObject("visabilityMessage", "All Hotels");
		modelAndView.addObject("hotel", hotelService.findByVerifiedEqualsTrue());
		modelAndView.addObject("allRooms", roomService.findAll());
		return modelAndView;
	}

	@GetMapping("CancelBackToMain")
	public ModelAndView cancelBackToMain(HttpSession session) {
		Object idFromSession = session.getAttribute("BOOKINGID");
		String bookingIdString = idFromSession.toString();
		Long bookingId = Long.parseLong(bookingIdString);

		bookingService.deleteById(bookingId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mainScreen.jsp");
		modelAndView.addObject("ownerMessage", "Booking Cancelled");
		modelAndView.addObject("visabilityMessage", "All Hotels");
		modelAndView.addObject("hotel", hotelService.findByVerifiedEqualsTrue());
		modelAndView.addObject("allRooms", roomService.findAll());

		return modelAndView;

	}

	@PostMapping("SearchByRoomType")
	public ModelAndView searchByRoomType(@ModelAttribute("room") Room room, ModelMap model) {
		List<Hotel> hotelList = hotelService.findByVerifiedAndRoomType(room.getRoomType());
		ModelAndView modelAndView = new ModelAndView();
		if (hotelList.isEmpty()) {
			modelAndView.setViewName("mainScreen.jsp");
			modelAndView.addObject("errorRoomTypeMessage", "No Rooms of that type");
			modelAndView.addObject("visabilityMessage", "All Hotels");
			modelAndView.addObject("hotel", hotelService.findByVerifiedEqualsTrue());
			modelAndView.addObject("allRooms", roomService.findAll());
			return modelAndView;
		}
		modelAndView.setViewName("mainScreen.jsp");
		modelAndView.addObject("hotel", hotelList);
		modelAndView.addObject("visabilityMessage", "Hotels With " + room.getRoomType() + " rooms");
		modelAndView.addObject("allRooms", roomService.findAll());
		return modelAndView;
	}

	@PostMapping("SearchByAvailability")
	public ModelAndView searchbyAvailability(
			@RequestParam(name = "checkInDate", defaultValue = "") String checkInDateString,
			@RequestParam(name = "checkOutDate", defaultValue = "") String checkOutDateString, ModelMap model) {
		LocalDate checkInDate = LocalDate.parse(checkInDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate checkOutDate = LocalDate.parse(checkOutDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		List<Hotel> hotelList = hotelService.findByAvailabilityAndVerifiedWithSpecifiedDates(checkInDate, checkOutDate);
		ModelAndView modelAndView = new ModelAndView();
		if (hotelList.isEmpty()) {
			modelAndView.setViewName("mainScreen.jsp");
			modelAndView.addObject("errorAvailabilityMessage", "No Rooms available");
			modelAndView.addObject("visabilityMessage", "All Hotels");
			modelAndView.addObject("hotel", hotelService.findByVerifiedEqualsTrue());
			modelAndView.addObject("allRooms", roomService.findAll());
			return modelAndView;
		}

		modelAndView.setViewName("mainScreen.jsp");
		modelAndView.addObject("visabilityMessage", "Hotels available between " + checkInDate + " and " + checkOutDate);
		modelAndView.addObject("hotel", hotelList);
		modelAndView.addObject("allRooms", roomService.findAll());
		return modelAndView;

	}

}
