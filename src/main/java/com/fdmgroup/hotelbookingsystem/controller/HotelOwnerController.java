package com.fdmgroup.hotelbookingsystem.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.BookingService;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.RoomService;

@Controller
public class HotelOwnerController {

	@Autowired
	HotelOwnerService hotelOwnerService;

	@Autowired
	HotelService hotelService;

	@Autowired
	RoomService roomService;

	@Autowired
	BookingService bookingService;

	@RequestMapping("OwnerHotels")
	public ModelAndView ownerHotels() {
		return new ModelAndView("WEB-INF/ownerHotels.jsp", "hotels", hotelService.findAll());
	}

	@GetMapping("AddHotel")
	public ModelAndView addHotels(HttpSession session) {
		Object idFromSession = session.getAttribute("HOTELOWNERID");
		String hotelOwnerIdString = idFromSession.toString();
		Long hotelOwnerId = Long.parseLong(hotelOwnerIdString);

		ModelAndView modelAndView = new ModelAndView("WEB-INF/addHotel.jsp");
		modelAndView.addObject("hotel", new Hotel());
		modelAndView.addObject("allRooms", roomService.findAll());
		modelAndView.addObject("hotelOwner", hotelOwnerService.retrieveOne(hotelOwnerId));
		return modelAndView;
	}

	@PostMapping("AddHotelSubmit")
	public ModelAndView addHotelSubmit(@ModelAttribute("hotel") Hotel hotel, ModelMap model, HttpSession session) {
		Optional<Hotel> hotelFromDB = hotelService.findByAddress(hotel.getAddress());
		Object idFromSession = session.getAttribute("HOTELOWNERID");
		String hotelOwnerIdString = idFromSession.toString();
		Long hotelOwnerId = Long.parseLong(hotelOwnerIdString);
		ModelAndView modelAndView = new ModelAndView();
		if (hotelFromDB.isPresent()) {
			modelAndView.addObject("errorMessage", "Hotel at that address already exists");
			modelAndView.addObject("hotelOwner", hotelOwnerService.retrieveOne(hotelOwnerId));
			modelAndView.setViewName("WEB-INF/addHotel.jsp");
			return modelAndView;
		}
		if (hotel.isAirportTransfers() != true) {
			hotel.setTransferPrice(0);
		}
		hotelService.save(hotel);
		modelAndView.addObject("successMessage",
				"Hotel has been added to system, Hotel will be visible once processed by an Administrator");
		modelAndView.setViewName("WEB-INF/ownerHotels.jsp");
		modelAndView.addObject("hotelOwner", hotelOwnerService.retrieveOne(hotelOwnerId));
		return modelAndView;
	}

	@GetMapping("EditHotel")
	public ModelAndView editHotel(@RequestParam("hotelId") long hotelId) {
		ModelAndView modelAndView = new ModelAndView("WEB-INF/editHotel.jsp");
		modelAndView.addObject("allRooms", roomService.findAll());
		modelAndView.addObject("hotel", hotelService.retrieveOne(hotelId));
		return modelAndView;
	}

	@PostMapping("EditHotelSubmit")
	public ModelAndView editHotelSubmit(@ModelAttribute("hotel") Hotel hotel, HttpSession session) {
		hotelService.save(hotel);
		Object idFromSession = session.getAttribute("HOTELOWNERID");
		String hotelOwnerIdString = idFromSession.toString();
		Long hotelOwnerId = Long.parseLong(hotelOwnerIdString);

		return new ModelAndView("WEB-INF/ownerHotels.jsp", "hotelOwner", hotelOwnerService.retrieveOne(hotelOwnerId));
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
