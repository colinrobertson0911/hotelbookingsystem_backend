package com.fdmgroup.hotelbookingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Extras;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.services.BookingService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
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

    MockMvc mockMvc;

    MockHttpSession session;

    final static String HOTEL_ROOT_URI = "/hotel";

    @BeforeEach
    public void setUp() {
        this.session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SharedHttpSessionConfigurer.sharedHttpSession())
                .build();
    }

    @Test
	public void test_ThatABookingCanBeRetrieved() throws Exception {
        ResultActions mvcResult = this.mockMvc.perform(get(HOTEL_ROOT_URI + "/BookingConfirmation/1")
                .session(session))
                .andExpect(status().isOk());
        String expectedResult = "{\"bookingId\":1,\"roomType\":\"STANDARD\",\"hotel\":\"Travellodge Glasgow\",\"checkInDate\":\"2020-04-23\",\"checkOutDate\":\"2020-04-27\",\"roomPrice\":60.00,\"extrasPrice\":20.00,\"totalPrice\":440.00,\"extras\":\"AIRPORTTRANSFER\",\"checkInDateFormatted\":\"23/04/2020\",\"checkOutDateFormatted\":\"27/04/2020\"}";
        Assertions.assertEquals(expectedResult, mvcResult.andReturn()
                .getResponse().getContentAsString());

	}

	@Test
	public void test_ThatPriceTotalCanBeCalculated() throws Exception {
		LocalDate checkInDate = LocalDate.of(2020, 06, 20);
		LocalDate checkOutDate = LocalDate.of(2020, 06, 27);
		Hotel hotel = hotelService.retrieveOne(1L).get();
		Bookings booking = new Bookings();
		booking.setRoomType("STANDARD");
		booking.setHotel(hotel.getHotelName());
		booking.setCheckInDate(checkInDate);
		booking.setCheckOutDate(checkOutDate);
		booking.setRoomPrice(new BigDecimal("15.00"));
		booking.setExtras(Extras.AIRPORTTRANSFER);
		booking.setExtrasPrice(new BigDecimal("15.00"));
		BigDecimal totalPrice = bookingService.calculateTotalPrice(booking);
        this.mockMvc.perform(post(HOTEL_ROOT_URI + "/BookingSubmit")
                .session(session)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk());
		Assertions.assertEquals(totalPrice, new BigDecimal("105.00"));
	}

	@Test
	public void test_ThatPriceTotalCanBeCalculatedLessThanFiveDays() throws Exception {
		LocalDate checkInDate = LocalDate.of(2020, 06, 20);
		LocalDate checkOutDate = LocalDate.of(2020, 06, 22);
		Hotel hotel = hotelService.retrieveOne(1L).get();
		Bookings booking = new Bookings();
		booking.setRoomType("STANDARD");
		booking.setHotel(hotel.getHotelName());
		booking.setCheckInDate(checkInDate);
		booking.setCheckOutDate(checkOutDate);
		booking.setRoomPrice(new BigDecimal("15.00"));
		booking.setExtras(Extras.AIRPORTTRANSFER);
		booking.setExtrasPrice(new BigDecimal("15.00"));
		BigDecimal totalPrice = bookingService.calculateTotalPrice(booking);
        this.mockMvc.perform(post(HOTEL_ROOT_URI + "/BookingSubmit")
                .session(session)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk());
        Assertions.assertEquals(totalPrice, new BigDecimal("45.00"));
	}

}
