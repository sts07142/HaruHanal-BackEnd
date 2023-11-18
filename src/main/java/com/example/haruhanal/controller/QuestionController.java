package com.example.haruhanal.controller;

import com.example.haruhanal.dto.QuestionDTO;
import com.example.haruhanal.entity.Question;
import com.example.haruhanal.service.QuestionService;
import com.example.haruhanal.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/questions")
@RequiredArgsConstructor
@Tag(name = "Question", description = "Question API")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;


    /**
     * 특정 리뷰 가져오기
     */
    @GetMapping("/{question_id}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable("question_id") Long id){
        Optional<Question> question = questionService.getQuestion(id);
        if (question.isPresent()) {
            QuestionDTO questionDTO = new QuestionDTO(question.get());
            return ResponseEntity.ok(questionDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 리뷰 생성
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createQuestion(@RequestBody QuestionDTO questionDTO) {
        Question question = Question.builder()
                .title(questionDTO.getTitle())
                .text(questionDTO.getText())
                .answer(questionDTO.getAnswer())
                .user(userService.getUser(questionDTO.getUserId()).get())
                .build();
        Long savedQuestionId = questionService.saveQuestion(question);
        return ResponseEntity.ok(savedQuestionId);
    }

    /**
     * 특정 리뷰 업데이트
     */
    @PutMapping("/{question_id}")
    public ResponseEntity<Long> updateQuestion(@PathVariable("question_id") Long id, @RequestBody QuestionDTO questionDTO) {
        Optional<Question> savedQuestion = questionService.getQuestion(id);
        if (savedQuestion.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Question question = Question.builder()
                .title(questionDTO.getTitle())
                .text(questionDTO.getText())
                .build();

        questionService.updateQuestion(id, question);
        return ResponseEntity.ok(id);
    }

    /**
     * 특정 리뷰 삭제
     */
    @DeleteMapping("/{question_id}")
    public ResponseEntity<Long> deleteQuestion(@PathVariable("question_id") Long id) {
        Optional<Question> savedQuestion = questionService.getQuestion(id);
        if (savedQuestion.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Long deletedQuestionId = questionService.deleteQuestion(id);
        return ResponseEntity.ok(deletedQuestionId);
    }

}
