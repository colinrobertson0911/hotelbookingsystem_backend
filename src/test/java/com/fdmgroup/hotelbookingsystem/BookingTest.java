package com.fdmgroup.hotelbookingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.hotelbookingsystem.model.Booking;
import com.fdmgroup.hotelbookingsystem.model.Extras;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.BookingService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookingTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

	@Autowired
	HotelService hotelService;

	@Autowired
	BookingService bookingService;

	@Autowired
	RoomService roomService;

    MockMvc mockMvc;

    MockHttpSession session;

    final static String BOOKING_ROOT_URI = "/booking";


	@BeforeEach
    public void setUp() {
        this.session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SharedHttpSessionConfigurer.sharedHttpSession())
                .build();
    }


	@Test
	public void test_ThatABookingCanBeCreatedWithInvalidDates() throws Exception{

		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("customer1");

		LocalDate checkInDate = LocalDate.of(2020, 06, 29);
		LocalDate checkOutDate = LocalDate.of(2020, 06, 27);
		Hotel hotel = hotelService.findById(1L).get();
		Booking booking = new Booking();
		booking.setRoomType("STANDARD");
		booking.setHotel(hotel.getHotelName());
		booking.setCheckInDate(checkInDate);
		booking.setCheckOutDate(checkOutDate);
		Room room = roomService.findByRoomType(booking.getRoomType()).get(0);
		booking.setRoomPrice(room.getPrice());
		booking.setExtras(Extras.AIRPORTTRANSFER);
		booking.setExtrasPrice(new BigDecimal("15.00"));
		BigDecimal totalPrice = bookingService.calculateTotalPrice(booking);

		this.mockMvc.perform(post(BOOKING_ROOT_URI + "/BookingSubmit")
				.session(session)
				.principal(mockPrincipal)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(booking)))
				.andExpect(status().isBadRequest());
	}

    @Test
	public void test_ThatABookingCanBeRetrieved() throws Exception {
    	
        ResultActions mvcResult = this.mockMvc.perform(get(BOOKING_ROOT_URI + "/BookingConfirmation/1")
                .session(session))
                .andExpect(status().isOk());
        String expectedResult = "{\"bookingId\":1,\"roomType\":\"STANDARD\",\"hotel\":\"Travelodge Glasgow\",\"checkInDate\":\"2020-07-23\",\"checkOutDate\":\"2020-07-27\",\"roomPrice\":60.00,\"extrasPrice\":20.00,\"totalPrice\":440.00,\"extras\":\"AIRPORTTRANSFER\"}";
        Assertions.assertEquals(expectedResult, mvcResult.andReturn()
                .getResponse().getContentAsString());

	}

	@Test
	public void test_ThatPriceTotalCanBeCalculated() throws Exception {

		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("customer1");

		LocalDate checkInDate = LocalDate.of(2020, 12, 20);
		LocalDate checkOutDate = LocalDate.of(2020, 12, 27);
		Hotel hotel = hotelService.findById(1L).get();
		Booking booking = new Booking();
		booking.setRoomType("STANDARD");
		booking.setHotel(hotel.getHotelName());
		booking.setCheckInDate(checkInDate);
		booking.setCheckOutDate(checkOutDate);
		Room room = roomService.findByRoomType(booking.getRoomType()).get(0);
		booking.setRoomPrice(room.getPrice());
		booking.setExtras(Extras.AIRPORTTRANSFER);
		booking.setExtrasPrice(new BigDecimal("15.00"));
		BigDecimal totalPrice = bookingService.calculateTotalPrice(booking);
        this.mockMvc.perform(post(BOOKING_ROOT_URI + "/BookingSubmit")
                .session(session)
				.principal(mockPrincipal)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated());
		Assertions.assertEquals(totalPrice, new BigDecimal("380.00"));
	}

	@Test
	public void test_ThatPriceTotalCanBeCalculatedLessThanFiveDays() throws Exception {

		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("customer1");

		LocalDate checkInDate = LocalDate.of(2020, 12, 20);
		LocalDate checkOutDate = LocalDate.of(2020, 12, 22);
		Hotel hotel = hotelService.findById(1L).get();
		Booking booking = new Booking();
		booking.setRoomType("STANDARD");
		booking.setHotel(hotel.getHotelName());
		booking.setCheckInDate(checkInDate);
		booking.setCheckOutDate(checkOutDate);
		Room room = roomService.findByRoomType(booking.getRoomType()).get(0);
		booking.setRoomPrice(room.getPrice());
		booking.setExtras(Extras.AIRPORTTRANSFER);
		BigDecimal totalPrice = bookingService.calculateTotalPrice(booking);
        this.mockMvc.perform(post(BOOKING_ROOT_URI + "/BookingSubmit")
                .session(session)
				.principal(mockPrincipal)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated());
        Assertions.assertEquals(totalPrice, new BigDecimal("140.00"));
	}
	
	

}
