package com.example.haruhanal.controller;

import com.example.haruhanal.dto.UserDTO;
import com.example.haruhanal.entity.User;
import com.example.haruhanal.enums.Gender;
import com.example.haruhanal.enums.UserRole;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Test
    public void testGetUser() throws Exception {
        Long userId = 1L;
        User user = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .age(24)
                .email("1234@gmail.com")
                .address("korea")
                .userRole(UserRole.valueOf("USER"))
                .subscribe(1)
                .build();

        when(userService.getUser(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/v1/user/{user_id}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.gender").value(user.getGender().toString()))
                .andExpect(jsonPath("$.age").value(user.getAge()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.address").value(user.getAddress()))
                .andExpect(jsonPath("$.userRole").value(user.getUserRole().toString()))
                .andExpect(jsonPath("$.subscribe").value(user.getSubscribe()));
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .age(24)
                .email("1234@gmail.com")
                .address("korea")
                .userRole(UserRole.valueOf("USER"))
                .subscribe(1)
                .build();

        mockMvc.perform(post("/v1/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .age(24)
                .email("1234@gmail.com")
                .address("korea")
                .userRole(UserRole.valueOf("USER"))
                .subscribe(1)
                .build();

        Long saveId = userService.saveUser(user);

        UserDTO userDTO = UserDTO.builder()
                .name("heesang")
                .email("1234@gmail.com")
                .address("korea")
                .build();

        mockMvc.perform(put("/v1/user/{user_id}", saveId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void testUpdateUserSubscribe() throws Exception {
        User user = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .age(24)
                .email("1234@gmail.com")
                .address("korea")
                .userRole(UserRole.valueOf("USER"))
                .subscribe(1)
                .build();

        Long saveId = userService.saveUser(user);

        UserDTO userDTO = UserDTO.builder()
                .subscribe(0)
                .build();

        mockMvc.perform(put("/v1/user/subscribe/{user_id}", saveId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/v1/user/{user_id}", userId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}