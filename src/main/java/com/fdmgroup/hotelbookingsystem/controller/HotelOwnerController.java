package com.fdmgroup.hotelbookingsystem.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.BookingService;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.RoomService;

@RestController
@RequestMapping("hotelbookingsystem/hotelOwner")
@CrossOrigin(origins = "http://localhost:4200")
public class HotelOwnerController {

	@Autowired
	HotelOwnerService hotelOwnerService;

	@Autowired
	HotelService hotelService;

	@Autowired
	RoomService roomService;

	@Autowired
	BookingService bookingService;


	@PostMapping("/AddHotelSubmit/{hotelOwnerId}")
	public ResponseEntity<HttpStatus> addHotelSubmit(@PathVariable("hotelOwnerId")long hotelOwnerId, @RequestBody Hotel hotel) {
		Optional<Hotel> hotelFromDB = hotelService.findByAddress(hotel.getAddress());
		if (hotelFromDB.isPresent()) {
			return new ResponseEntity<HttpStatus> (HttpStatus.IM_USED);
		}
		if (hotel.isAirportTransfers() != true) {
			hotel.setTransferPrice(0);
		}
		try {
			hotelService.save(hotel);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<HttpStatus> (HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@PutMapping("/EditHotelSubmit/{hotelOwnerId}")
	public ResponseEntity<Hotel> editHotelSubmit(@PathVariable("hotelOwnerId") long hotelOwnerId, @RequestBody Hotel hotel) {
		return ResponseEntity.ok(hotelService.save(hotel));
	}

	@GetMapping("AllBookings")
	public ModelAndView allBookings(@RequestParam("hotelId") Long hotelId) {
		ModelAndView modelAndView = new ModelAndView("WEB-INF/allBookings.jsp");
		modelAndView.addObject("bookings", bookingService.findAll());
		modelAndView.addObject("hotel", hotelService.retrieveOne(hotelId));
		return modelAndView;
	}

	@GetMapping("ReturnToOwnerScreen")
	public ModelAndView returnToOwnerScreen(HttpSession session) {
		Object idFromSession = session.getAttribute("HOTELOWNERID");
		String hotelOwnerIdString = idFromSession.toString();
		Long hotelOwnerId = Long.parseLong(hotelOwnerIdString);

		ModelAndView modelAndView = new ModelAndView("WEB-INF/ownerHotels.jsp");
		modelAndView.addObject("hotelOwner", hotelOwnerService.retrieveOne(hotelOwnerId));
		return modelAndView;
	}

	@GetMapping("NewRoomType")
	public ModelAndView newRoomType() {
		ModelAndView modelAndView = new ModelAndView("WEB-INF/newRoomType.jsp");
		modelAndView.addObject("room", new Room());
		return modelAndView;
	}

	@PostMapping("AddNewRoomTypeSubmit")
	public ModelAndView newRoomTypeSubmit(@ModelAttribute("room") Room room, ModelMap model, HttpSession session) {
		Object idFromSession = session.getAttribute("HOTELOWNERID");
		String hotelOwnerIdString = idFromSession.toString();
		Long hotelOwnerId = Long.parseLong(hotelOwnerIdString);
		ModelAndView modelAndView = new ModelAndView();
		Optional<Room> roomFromDB = roomService.findByRoomTypeAndPrice(room.getRoomType(), room.getPrice());
		if (roomFromDB.isPresent()) {
			modelAndView.setViewName("WEB-INF/newRoomType.jsp");
			modelAndView.addObject("errorMessage", "Room type and Price already exist");
			modelAndView.addObject("room", new Room());
			return modelAndView;
		}
		roomService.save(room);
		modelAndView.setViewName("WEB-INF/ownerHotels.jsp");
		modelAndView.addObject("hotelOwner", hotelOwnerService.retrieveOne(hotelOwnerId));
		modelAndView.addObject("successMessage", "Room type successfully created");
		return modelAndView;
	}

	@GetMapping("Refresh")
	public ModelAndView refreshHotelList(HttpSession session) {
		Object idFromSession = session.getAttribute("HOTELOWNERID");
		String hotelOwnerIdString = idFromSession.toString();
		Long hotelOwnerId = Long.parseLong(hotelOwnerIdString);
		ModelAndView modelAndView = new ModelAndView("WEB-INF/ownerHotels.jsp");
		modelAndView.addObject("hotelOwner", hotelOwnerService.retrieveOne(hotelOwnerId));
		return modelAndView;
	}

}
