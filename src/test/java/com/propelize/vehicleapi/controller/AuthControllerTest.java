package com.propelize.vehicleapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.propelize.vehicleapi.dto.AuthRequest;
import com.propelize.vehicleapi.dto.AuthResponse;
import com.propelize.vehicleapi.model.User;
import com.propelize.vehicleapi.service.JwtUtil;
import com.propelize.vehicleapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import({com.propelize.vehicleapi.config.NoSecurityConfig.class, com.propelize.vehicleapi.exception.GlobalExceptionHandler.class})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/auth";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() throws Exception {
        AuthRequest request = new AuthRequest("newuser", "newpass");
        User savedUser = new User("newuser", "newpass");

        when(userService.saveUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post(BASE_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("newuser")));
    }

    @Test
    public void testRegister_Conflict_UserAlreadyExists() throws Exception {
        AuthRequest request = new AuthRequest("existinguser", "pass");

        when(userService.saveUser(any(User.class)))
                .thenThrow(new RuntimeException("Ce nom d'utilisateur existe déjà."));

        mockMvc.perform(post(BASE_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Ce nom d'utilisateur existe déjà."));
    }

    @Test
    public void testLogin_Success() throws Exception {
        AuthRequest request = new AuthRequest("testuser", "testpass");
        String token = "mocked-jwt-token";
        User user = new User("testuser", "testpass");

        when(userService.authenticate(any(AuthRequest.class))).thenReturn(token);
        when(userService.findByName("testuser")).thenReturn(Optional.of(user));

        mockMvc.perform(post(BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(token)))
                .andExpect(jsonPath("$.userName", is("testuser")))
                .andExpect(jsonPath("$.serverTime").exists());
    }

    @Test
    public void testLogin_UserNotFound() throws Exception {
        AuthRequest request = new AuthRequest("nouser", "pass");

        when(userService.authenticate(any(AuthRequest.class))).thenReturn("some-token");
        when(userService.findByName("nouser")).thenReturn(Optional.empty());

        mockMvc.perform(post(BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        AuthRequest request = new AuthRequest("user", "wrongpass");

        when(userService.authenticate(any(AuthRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(post(BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}
