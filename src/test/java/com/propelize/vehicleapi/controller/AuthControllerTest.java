package com.propelize.vehicleapi.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.propelize.vehicleapi.controller.AuthController;
import com.propelize.vehicleapi.dto.AuthRequest;
import com.propelize.vehicleapi.service.UserService;
import com.propelize.vehicleapi.service.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        AuthRequest authRequest = new AuthRequest("username", "password");
        String token = "token";

        when(userService.authenticate(any(AuthRequest.class))).thenReturn(token);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"username\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    public void testAuthenticateUser_InvalidCredentials() throws Exception {
        AuthRequest authRequest = new AuthRequest("username", "wrongpassword");

        when(userService.authenticate(any(AuthRequest.class))).thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"username\",\"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized());
    }
}