package com.fdmgroup.hotelbookingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("hotelOwner1")
class HotelOwnerTest {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	HotelOwnerService hotelOwnerService;

	@Autowired
	HotelService hotelService;
	
	MockMvc mockMvc;

	MockHttpSession session;

	private static ValidatorFactory validatorFactory;
	private static Validator validator;
final static String HOTELOWNER_ROOT_URI = "/hotelOwner";
	
	@BeforeEach
	public void setUp() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SharedHttpSessionConfigurer.sharedHttpSession())
				.build();
	}
	
	@Test
	public void addHotelThatIsValid() throws Exception {
		Hotel hotel = new Hotel("Glasgow Hotel", 100, "Center of Glasgow", "G something", "Glasgow", "TV and bed", null, 4, null, false, 0, false);
		this.mockMvc.perform(post(HOTELOWNER_ROOT_URI + "/AddHotelSubmit")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(hotel)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void addHotelThatIsInDatabase() throws Exception {
		Hotel hotel = new Hotel("Travelodge Glasgow", 2, "1 main street", "g43 6pq", "Glasgow", "none");
		this.mockMvc.perform(post(HOTELOWNER_ROOT_URI + "/AddHotelSubmit")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(hotel)))
				.andExpect(status().isImUsed());
	}
	
	@Test public void addHotelThatIsNotValidValidator() throws Exception {
		Hotel hotel = new Hotel();

		 Set<ConstraintViolation<Hotel>> violations = validator.validate(hotel);

		 assertEquals(violations.size(), 1);
	}

	@Test
	public void editHotel() throws Exception {
		Hotel hotel = hotelService.findById(1).get();
		hotel.setHotelName("The awesome hotel");
		this.mockMvc.perform(put(HOTELOWNER_ROOT_URI + "/EditHotelSubmit/")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(hotel)))
				.andExpect(status().isOk());
		Hotel updatedHotel = hotelService.findById(1).get();
		Assertions.assertNotEquals(hotel, updatedHotel);
	}

	@Test
	public void editHotelWithInvalidId() throws Exception {
		Hotel hotel = hotelService.findById(1).get();
		hotel.setHotelId(123);
		hotel.setHotelName("The awesome hotel");
		this.mockMvc.perform(put(HOTELOWNER_ROOT_URI + "/EditHotelSubmit/")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(hotel)))
				.andExpect(status().isNotFound());
	}

	@Test
	public void addNewRoomType() throws Exception {
		Room room = new Room("HONEYMOON", new BigDecimal("150.00"));
		this.mockMvc.perform(post(HOTELOWNER_ROOT_URI + "/AddNewRoomTypeSubmit")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(room)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void addNewRoomThatIsInDatabase() throws Exception {
		Room room = new Room("STANDARD", new BigDecimal("60.00"));
		this.mockMvc.perform(post(HOTELOWNER_ROOT_URI + "/AddNewRoomTypeSubmit")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(room)))
				.andExpect(status().isImUsed());
	}
	
	@Test
	public void addRoomThatIsNotValid() throws Exception {
		Room room = new Room("STANDARD", new BigDecimal("0.0"));

		Set<ConstraintViolation<Room>> violations
				= validator.validate(room);

		assertEquals(violations.size(), 1);
	}
	
}
