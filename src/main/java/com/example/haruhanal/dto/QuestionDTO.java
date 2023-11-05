package com.example.haruhanal.dto;

import com.example.haruhanal.entity.Question;
import lombok.Builder;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String text;
    private String answer;
    private Long userId;


    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.text = question.getText();
        this.answer = question.getAnswer();
        this.userId = question.getUser().getId();

    }
    @Builder
    public QuestionDTO(Long id, String title, String text, String answer, Long userId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.answer = answer;
        this.userId = userId;
    }
}
