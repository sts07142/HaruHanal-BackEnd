package com.example.haruhanal.controller;

import com.example.haruhanal.dto.ReviewDTO;
import com.example.haruhanal.entity.Review;
import com.example.haruhanal.entity.User;
import com.example.haruhanal.enums.Gender;
import com.example.haruhanal.enums.UserRole;
import com.example.haruhanal.service.ReviewService;
import com.example.haruhanal.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetReview() throws Exception {
        User user = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .age(24)
                .email("1234@gmail.com")
                .userRole(UserRole.valueOf("USER"))
                .build();

        Review mockReview = Review.builder()
                .id(1L)
                .title("Test Title")
                .text("Test Text")
                .review_image_url("123.com")
                .user(user)
                .build();

        when(reviewService.getReview(1L)).thenReturn(Optional.of(mockReview));

        mockMvc.perform(get("/v1/reviews/{review_id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.text").value("Test Text"))
                .andExpect(jsonPath("$.review_image_url").value("123.com"));
    }

    @Test
    void testCreateReview() throws Exception {
        User user = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .age(24)
                .email("1234@gmail.com")
                .userRole(UserRole.valueOf("USER"))
                .build();

        when(userService.getUser(1L)).thenReturn(Optional.of(user));

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .title("Test Title")
                .text("Test Text")
                .review_image_url("123.com")
                .userId(1L)
                .build();

        mockMvc.perform(post("/v1/reviews/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reviewDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void testUpdateReview() throws Exception {
        ReviewDTO updatedReviewDTO = ReviewDTO.builder()
                .id(1L)
                .title("Updated Title")
                .text("Updated Text")
                .review_image_url("456.com")
                .userId(1L)
                .build();

        mockMvc.perform(put("/v1/reviews/{review_id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedReviewDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void testDeleteReview() throws Exception {
        Long mockReviewId = 1L;

        when(reviewService.deleteReview(mockReviewId)).thenReturn(mockReviewId);

        mockMvc.perform(delete("/v1/reviews/{review_id}", mockReviewId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}