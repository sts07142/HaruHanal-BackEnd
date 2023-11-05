package com.example.haruhanal.repository;

import com.example.haruhanal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
