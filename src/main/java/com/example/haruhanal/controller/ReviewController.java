package com.example.haruhanal.controller;

import com.example.haruhanal.dto.ReviewDTO;
import com.example.haruhanal.entity.Review;
import com.example.haruhanal.service.ReviewService;
import com.example.haruhanal.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "Review API")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    /**
     * 전체 리뷰 가져오기
     */
    @GetMapping("/all")
    public Page<ReviewDTO> searchReview(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> pages = reviewService.getAllReview(pageable);
        return pages.map(ReviewDTO::new);
    }
    
    /**
     * 특정 리뷰 가져오기
     */
    @GetMapping("/{review_id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable("review_id") Long id){
        Optional<Review> review = reviewService.getReview(id);
        if (review.isPresent()) {
            ReviewDTO reviewDTO = new ReviewDTO(review.get());
            return ResponseEntity.ok(reviewDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 리뷰 생성
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .title(reviewDTO.getTitle())
                .text(reviewDTO.getText())
                .review_image_url(reviewDTO.getReview_image_url())
                .user(userService.getUser(reviewDTO.getUserId()).get())
                .build();
        Long savedReviewId = reviewService.saveReview(review);
        return ResponseEntity.ok(savedReviewId);
    }

    /**
     * 특정 리뷰 업데이트
     */
    @PutMapping("/{review_id}")
    public ResponseEntity<Long> updateReview(@PathVariable("review_id") Long id, @RequestBody ReviewDTO reviewDTO) {
        Optional<Review> savedReview = reviewService.getReview(id);
        if (savedReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Review review = Review.builder()
                .title(reviewDTO.getTitle())
                .text(reviewDTO.getText())
                .review_image_url((reviewDTO.getReview_image_url()))
                .build();

        reviewService.updateReview(id, review);
        return ResponseEntity.ok(id);
    }

    /**
     * 특정 리뷰 삭제
     */
    @DeleteMapping("/{review_id}")
    public ResponseEntity<Long> deleteReview(@PathVariable("review_id") Long id) {
        Optional<Review> savedReview = reviewService.getReview(id);
        if (savedReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Long deletedReviewId = reviewService.deleteReview(id);
        return ResponseEntity.ok(deletedReviewId);
    }

}
