package com.fdmgroup.hotelbookingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;

@Controller
public class AdminController {

	@Autowired
	HotelOwnerService hotelOwnerService;

	@Autowired
	HotelService hotelService;

	@GetMapping("AllOwners")
	public ModelAndView hotelOwners() {
		return new ModelAndView("WEB-INF/allOwners.jsp", "hotelOwners", hotelOwnerService.findAll());

	}

	@GetMapping("SeeHotelOwner")
	public ModelAndView editHotelOwners(@RequestParam("hotelOwnerId") long hotelOwnerId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("WEB-INF/editOwners.jsp");
		modelAndView.addObject("hotelOwner", hotelOwnerService.retrieveOne(hotelOwnerId));
		modelAndView.addObject("allHotels", hotelService.findAll());
		return modelAndView;
	}

	@PostMapping("EditHotelOwnerSubmit")
	public ModelAndView hotelOwnersUpdated(@ModelAttribute("hotelOwner") HotelOwner hotelOwner) {
		hotelOwnerService.save(hotelOwner);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("WEB-INF/allOwners.jsp");
		modelAndView.addObject("hotelOwners", hotelOwnerService.findAll());
		modelAndView.addObject("successMessage", "Owner Successfully Updated");
		return modelAndView;
	}

	@GetMapping("AllHotels")
	public ModelAndView allHotels() {
		return new ModelAndView("WEB-INF/allHotels.jsp", "hotels", hotelService.findAll());
	}

	@GetMapping("VerifyHotel")
	public ModelAndView verifyHotel(@ModelAttribute("hotelId") long hotelId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("WEB-INF/verifyHotel.jsp");
		modelAndView.addObject("hotel", hotelService.retrieveOne(hotelId));
		return modelAndView;
	}

	@PostMapping("VerifyHotelSubmit")
	public ModelAndView verifyHotelSumbit(@ModelAttribute("hotel") Hotel hotel) {
		hotelService.save(hotel);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("WEB-INF/allHotels.jsp");
		modelAndView.addObject("successMessage", "Hotel's Verification Status Changed");
		modelAndView.addObject("hotels", hotelService.findAll());
		return modelAndView;
	}

}
