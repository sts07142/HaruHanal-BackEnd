package com.example.haruhanal.service;

import com.example.haruhanal.entity.Review;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ReviewServiceTest {
    @Autowired
    ReviewService reviewService;

    @Test
    public void 리뷰_생성() throws Exception {
        // given
        Review review = Review.builder()
                .title("추천합니다")
                .text("피로가 확 풀리는 느낌이에요")
                .review_image_url("123.com")
                .build();
        // when
        Long savedId = reviewService.saveReview(review);
        // then
        assertEquals(review, reviewService.getReview(savedId).get());
    }

    @Test
    public void 리뷰_업데이트() throws Exception {
        // given
        Review review1 = Review.builder()
                .title("추천합니다")
                .text("피로가 확 풀리는 느낌이에요")
                .review_image_url("123.com")
                .build();
        Review review2 = Review.builder()
                .title("아쉬워요")
                .text("좋아요")
                .review_image_url("123.com")
                .build();
        // when
        Long savedId = reviewService.saveReview(review1);
        // then
        try {
            reviewService.updateReview(savedId, review2);
        } catch (IllegalStateException e) {
            fail("업데이트 실패");
        }
    }

    @Test
    public void 리뷰_삭제() throws Exception {
        // given
        Review review1 = Review.builder()
                .title("추천합니다")
                .text("피로가 확 풀리는 느낌이에요")
                .review_image_url("123.com")
                .build();
        // when
        Long savedId = reviewService.saveReview(review1);
        reviewService.deleteReview(savedId);
        // then
        if (!reviewService.getReview(savedId).isEmpty()) {
            fail("삭제 실패");
        }
    }

}