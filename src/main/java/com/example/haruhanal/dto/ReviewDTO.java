package com.example.haruhanal.dto;

import com.example.haruhanal.entity.Review;
import lombok.Builder;
import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private String title;
    private String text;
    private String review_image_url;
    private Long userId;


    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.text = review.getText();
        this.review_image_url = review.getReview_image_url();
        this.userId = review.getUser().getId();

    }
    @Builder
    public ReviewDTO(Long id, String title, String text, String review_image_url, Long userId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.review_image_url = review_image_url;
        this.userId = userId;
    }
}
