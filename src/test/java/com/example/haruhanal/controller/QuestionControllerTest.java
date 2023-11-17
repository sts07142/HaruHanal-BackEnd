package com.example.haruhanal.controller;

import com.example.haruhanal.dto.QuestionDTO;
import com.example.haruhanal.entity.Question;
import com.example.haruhanal.entity.User;
import com.example.haruhanal.enums.Gender;
import com.example.haruhanal.enums.UserRole;
import com.example.haruhanal.service.QuestionService;
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
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private UserService userService;

    @Test
    void testGetQuestion() throws Exception {
        User user = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .age(24)
                .email("1234@gmail.com")
                .userRole(UserRole.valueOf("USER"))
                .build();

        Question mockQuestion = Question.builder()
                .id(1L)
                .title("Test Title")
                .text("Test Text")
                .answer("Test Answer")
                .user(user)
                .build();

        when(questionService.getQuestion(1L)).thenReturn(Optional.of(mockQuestion));

        mockMvc.perform(get("/v1/questions/{question_id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.text").value("Test Text"))
                .andExpect(jsonPath("$.answer").value("Test Answer"));
    }

    @Test
    void testCreateQuestion() throws Exception {
        User user = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .age(24)
                .email("1234@gmail.com")
                .userRole(UserRole.valueOf("USER"))
                .build();

        when(userService.getUser(1L)).thenReturn(Optional.of(user));

        QuestionDTO questionDTO = QuestionDTO.builder()
                .title("Test Title")
                .text("Test Text")
                .answer("Test Answer")
                .userId(1L)
                .build();

        mockMvc.perform(post("/v1/questions/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(questionDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void testUpdateQuestion() throws Exception {
        QuestionDTO updatedQuestionDTO = QuestionDTO.builder()
                .id(1L)
                .title("Updated Title")
                .text("Updated Text")
                .answer("456.com")
                .userId(1L)
                .build();

        mockMvc.perform(put("/v1/questions/{question_id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedQuestionDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void testDeleteQuestion() throws Exception {
        Long mockQuestionId = 1L;

        when(questionService.deleteQuestion(mockQuestionId)).thenReturn(mockQuestionId);

        mockMvc.perform(delete("/v1/questions/{question_id}", mockQuestionId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}