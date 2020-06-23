package com.fdmgroup.hotelbookingsystem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.hotelbookingsystem.model.Customer;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        review.setCustomer(customerService.findByUsername("customer1").get());
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
        review.setCustomer(customerService.findByUsername("customer1").get());
        review.setMessage("Your hotel sucks");
        review.setScore(2);
        reviewService.save(review);

        Optional<Review> reviewFromDB = reviewService.findByReviewId(review.getReviewId());

        assertEquals(reviewFromDB.get().getReviewId(), review.getReviewId());
    }

    @Test
    @WithUserDetails("customer1")
    public void addReviewThatIsValid() throws Exception {
        Hotel hotelForReview = hotelService.findByHotelId(1L);
        User customer = userService.findByUsername("customer1").get();

        Review review = new Review(hotelForReview, customer, "The hotel was great", 5);
        this.mockMvc.perform(post(REVIEW_ROOT_URI + "/createReview")
                .session(session)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isCreated());

        System.out.println(review);
    }

}
