package com.fdmgroup.hotelbookingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.Review;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.CustomerService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.ReviewService;
import com.fdmgroup.hotelbookingsystem.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithUserDetails("customer1")
public class ReviewTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    CustomerService customerService;

    @Autowired
    HotelService hotelService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    MockHttpSession session;

    final static String REVIEW_ROOT_URI = "/reviews";

    @BeforeEach
    public void setUp() {
        this.session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SharedHttpSessionConfigurer.sharedHttpSession())
                .build();
    }

    @Test
    public void test_ThatReviewCanBeCreated() {
        Review review = new Review();
        review.setHotel(hotelService.findById(1L).get());
        review.setCustomer(userService.findByUsername("customer1").get());
        review.setMessage("Your hotel sucks");
        review.setScore(2);

        int numberBeforeSaving = reviewService.findAll().size();
        reviewService.save(review);
        int numberAfterSaving = reviewService.findAll().size();

        assertNotEquals(numberBeforeSaving, numberAfterSaving);
    }

    @Test
    public void test_ThatAReviewCanBeRetrivedById() {
        Review review = new Review();
        review.setHotel(hotelService.findById(1L).get());
        review.setCustomer(userService.findByUsername("customer1").get());
        review.setMessage("Your hotel sucks");
        review.setScore(2);
        reviewService.save(review);

        Optional<Review> reviewFromDB = reviewService.findByReviewId(review.getReviewId());

        assertEquals(reviewFromDB.get().getReviewId(), review.getReviewId());
    }

    @Test
    public void addReviewThatIsValid() throws Exception {
        Hotel hotel = hotelService.findByHotelId(1L);
        User customerId = userService.findByUsername("customer1").get();

        Review review = new Review(hotel, customerId, "The hotel was great", 5);
        this.mockMvc.perform(post(REVIEW_ROOT_URI + "/createReview")
                .session(session)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isCreated());
    }

    @Test
    public void editReview() throws Exception {
        Review review = reviewService.findByReviewId(1L).get();
        review.setMessage("This hotel was ok");
        this.mockMvc.perform(put(REVIEW_ROOT_URI + "/editReview/1")
                .session(session)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk());
        Review updatedReview = reviewService.findByReviewId(1L).get();
        Assertions.assertNotEquals(review, updatedReview);
    }

    @Test
    public void viewAllReviewsByHotelId() throws Exception {
        this.mockMvc.perform(get(REVIEW_ROOT_URI +"/allReviews/1")
                .session(session)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAverageReview() throws Exception {
        this.mockMvc.perform(get(REVIEW_ROOT_URI + "/averageReview/1")
            .session(session)
            .contentType("application/json"))
                .andExpect(status().isOk());

    }

}
