package com.example.haruhanal.repository;

import com.example.haruhanal.entity.Question;
import com.example.haruhanal.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByUserId(Long user_id);
}
