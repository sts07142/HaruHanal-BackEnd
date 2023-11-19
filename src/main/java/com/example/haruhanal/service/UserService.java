package com.example.haruhanal.service;


import com.example.haruhanal.entity.User;
import com.example.haruhanal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Long saveUser(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public void updateUser(Long id, User updatedUser) {
        Optional<User> existedUser = userRepository.findById(id);

        if (existedUser.isPresent()) {
            User user = existedUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setAddress(updatedUser.getAddress());
        } else {
            throw new IllegalStateException("유저 업데이트에 실패하였습니다.");
        }
    }

    @Transactional
    public void updateUserCondition(Long id, String condition) {
        Optional<User> existedUser = userRepository.findById(id);

        if (existedUser.isPresent()) {
            User user = existedUser.get();
            user.setCondition(condition);
        } else {
            throw new IllegalStateException("유저 건강상태 업데이트에 실패하였습니다.");
        }
    }

    @Transactional
    public void updateUserSubscribe(Long id, Integer subscribe) {
        Optional<User> existedUser = userRepository.findById(id);

        if (existedUser.isPresent()) {
            User user = existedUser.get();
            user.setSubscribe(subscribe);
        } else {
            throw new IllegalStateException("유저 구독여부 업데이트에 실패하였습니다.");
        }
    }

    @Transactional
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
