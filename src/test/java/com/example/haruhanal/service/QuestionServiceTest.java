package com.example.haruhanal.service;

import com.example.haruhanal.entity.Question;
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
class QuestionServiceTest {
    @Autowired QuestionService questionService;

    @Test
    public void 질문_생성() throws Exception {
        // given
        Question question = Question.builder()
                .title("추천합니다")
                .text("너무 맛있어요")
                .answer("감사합니다")
                .build();
        // when
        Long savedId = questionService.saveQuestion(question);
        // then
        assertEquals(question, questionService.getQuestion(savedId).get());
    }

    @Test
    public void 질문_업데이트() throws Exception {
        // given
        Question question1 = Question.builder()
                .title("추천합니다")
                .text("너무 맛있어요")
                .answer("감사합니다")
                .build();
        Question question2 = Question.builder()
                .title("아쉬워요")
                .text("좋아요")
                .answer("감사합니다")
                .build();
        // when
        Long savedId = questionService.saveQuestion(question1);
        // then
        try {
            questionService.updateQuestion(savedId, question2);
        } catch (IllegalStateException e) {
            fail("업데이트 실패");
        }
    }

    @Test
    public void 질문_삭제() throws Exception {
        // given
        Question question1 = Question.builder()
                .title("추천합니다")
                .text("너무 맛있어요")
                .answer("감사합니다")
                .build();
        // when
        Long savedId = questionService.saveQuestion(question1);
        questionService.deleteQuestion(savedId);
        // then
        if (!questionService.getQuestion(savedId).isEmpty()) {
            fail("삭제 실패");
        }
    }
}