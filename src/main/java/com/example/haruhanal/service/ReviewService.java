package com.example.haruhanal.service;

import com.example.haruhanal.entity.Review;
import com.example.haruhanal.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long saveReview(Review review) {
        reviewRepository.save(review);
        return review.getId();
    }

    public Page<Review> getAllReview(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }
    public Optional<Review> getReview(Long review_id){
        return reviewRepository.findById(review_id);
    }

    public List<Review> getUserReview(Long user_id) {
        return reviewRepository.findByUserId(user_id);
    }
    @Transactional
    public void updateReview(Long id, Review updateReview) {
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.setTitle(updateReview.getTitle());
            review.setText(updateReview.getText());
            review.setReview_image_url(updateReview.getReview_image_url());

        } else {
            throw new IllegalStateException("리뷰 업데이트에 실패하였습니다.");
        }
    }
    @Transactional
    public Long deleteReview(Long id) {
        reviewRepository.deleteById(id);
        return id;
    }

}
