package com.example.haruhanal.entity;

import com.example.haruhanal.enums.Gender;
import com.example.haruhanal.enums.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
//    @Column(nullable = false)
    private String UID;
//    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    private String email;
    private Integer age;
    private String address;
//    @Column(name = "condition")
    private String condition;
    private Integer subscribe;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Question> questions = new ArrayList<>();

    @Builder
    public User(Long id, String UID, String password, String name, Gender gender, String email, Integer age, String address, String condition, Integer subscribe, UserRole userRole) {
        this.id = id;
        this.UID = UID;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.address = address;
        this.condition = condition;
        this.subscribe = subscribe;
        this.userRole = userRole;
    }

}
