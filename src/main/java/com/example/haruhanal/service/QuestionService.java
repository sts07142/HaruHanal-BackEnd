package com.example.haruhanal.service;

import com.example.haruhanal.entity.Question;
import com.example.haruhanal.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional
    public Long saveQuestion(Question question) {
        questionRepository.save(question);
        return question.getId();
    }
    public Optional<Question> getQuestion(Long question_id){
        return questionRepository.findById(question_id);
    }

    public List<Question> getUserQuestion(Long user_id) {
        return questionRepository.findByUserId(user_id);
    }
    @Transactional
    public void updateQuestion(Long id, Question updateQuestion) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);

        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setTitle(updateQuestion.getTitle());
            question.setText(updateQuestion.getText());

        } else {
            throw new IllegalStateException("Q&A 업데이트에 실패하였습니다.");
        }
    }

    @Transactional
    public void updateAnswer(Long id, String answer) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);

        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setAnswer(answer);

        } else {
            throw new IllegalStateException("Q&A 답변 업데이트에 실패하였습니다.");
        }
    }

    @Transactional
    public Long deleteQuestion(Long id) {
        questionRepository.deleteById(id);
        return id;
    }

}
