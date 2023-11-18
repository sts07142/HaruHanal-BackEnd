package com.example.haruhanal.controller;

import com.example.haruhanal.dto.QuestionDTO;
import com.example.haruhanal.dto.ReviewDTO;
import com.example.haruhanal.dto.UserDTO;
import com.example.haruhanal.entity.User;
import com.example.haruhanal.service.QuestionService;
import com.example.haruhanal.service.ReviewService;
import com.example.haruhanal.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {
    private final UserService userService;
    private final ReviewService reviewService;
    private final QuestionService questionService;

    /**
     * 유저 로그인
     */
    @GetMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        Optional<User> user = userService.getUserByEmail(userDTO.getEmail());
        if (user.isPresent()) {
            UserDTO userDto = new UserDTO(user.get());
            return ResponseEntity.ok(userDto);
        } else {
            User newUser = User.builder()
                    .name(userDTO.getName())
                    .email(userDTO.getEmail())
                    .build();

            Long savedUserId = userService.saveUser(newUser);
            return ResponseEntity.ok(userDTO);
        }

    }


    /**
     * 특정 유저 정보 가져오기
     */
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("user_id") Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            UserDTO userDto = new UserDTO(user.get());
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 특정 유저가 작성한 리뷰 가져오기
     */
    @GetMapping("/reviews/{user_id}")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable("user_id") Long id) {
        Optional<User> savedUser = userService.getUser(id);
        if (savedUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<ReviewDTO> reviewDTO = reviewService.getUserReview(id).stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviewDTO);
    }

    /**
     * 특정 유저가 작성한 문의사항 가져오기
     */
    @GetMapping("/questions/{user_id}")
    public ResponseEntity<List<QuestionDTO>> getQuestions(@PathVariable("user_id") Long id) {
        Optional<User> savedUser = userService.getUser(id);
        if (savedUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<QuestionDTO> questionDTO = questionService.getUserQuestion(id).stream()
                .map(QuestionDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(questionDTO);
    }

    /**
     * 유저 정보 생성
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createUser(@RequestBody UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .gender((userDTO.getGender()))
                .email(userDTO.getEmail())
                .age(userDTO.getAge())
                .address(userDTO.getAddress())
                .userRole(userDTO.getUserRole())
                .build();

        Long savedUserId = userService.saveUser(user);
        return ResponseEntity.ok(savedUserId);
    }

    /**
     * 유저 정보 업데이트
     */
    @PutMapping("/{user_id}")
    public ResponseEntity<Long> updateUser(@PathVariable("user_id") Long id, @RequestBody UserDTO userDTO) {
        Optional<User> savedUser = userService.getUser(id);
        if (savedUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .address(userDTO.getAddress())
                .build();
        userService.updateUser(id, user);
        return ResponseEntity.ok(id);
    }

    /**
     * 유저 건강정보 업데이트
     */
//    @PutMapping("/condition/{user_id}")
//    public ResponseEntity<Long> updateUserCondition(@PathVariable("user_id") Long id, @RequestBody UserDTO userDTO) {
//        Optional<User> savedUser = userService.getUser(id);
//        if (savedUser.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//        String condition = userDTO.getCondition();
//        userService.updateUserCondition(id, condition);
//        return ResponseEntity.ok(id);
//    }

    /**
     * 유저 구독여부 업데이트
     */
    @PutMapping("/subscribe/{user_id}")
    public ResponseEntity<Long> updateUserSubscribe(@PathVariable("user_id") Long id, @RequestBody UserDTO userDTO) {
        Optional<User> savedUser = userService.getUser(id);
        if (savedUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Integer sub = userDTO.getSubscribe();
        userService.updateUserSubscribe(id, sub);
        return ResponseEntity.ok(id);
    }


    /**
     * 유저 삭제
     */
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Long> deleteUser(@PathVariable("user_id") Long id) {
        Optional<User> savedUser = userService.getUser(id);
        if (savedUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Long deletedUserId = userService.deleteUser(id);
        return ResponseEntity.ok(deletedUserId);
    }
}
