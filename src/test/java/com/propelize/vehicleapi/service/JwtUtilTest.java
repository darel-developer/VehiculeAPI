package com.propelize.vehicleapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private static final String SECRET_KEY = "secret";
    private static final long EXPIRATION = 3600L; // en secondes (1h)
    private static final String USERNAME = "testUser";

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() throws Exception {
        jwtUtil = new JwtUtil();
        // Injection des valeurs via la réflexion
        Field secretField = JwtUtil.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(jwtUtil, SECRET_KEY);

        Field expirationField = JwtUtil.class.getDeclaredField("expiration");
        expirationField.setAccessible(true);
        expirationField.set(jwtUtil, EXPIRATION);
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken(USERNAME);
        assertNotNull(token);
        // Le token n'a pas de préfixe "Bearer "
        assertFalse(token.startsWith("Bearer "));
    }

    @Test
    void testValidateToken() {
        UserDetails userDetails = User.withUsername(USERNAME).password("pass").roles("USER").build();
        String token = jwtUtil.generateToken(userDetails);
        boolean isValid = jwtUtil.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void testExtractUsername() {
        String token = jwtUtil.generateToken(USERNAME);
        String username = jwtUtil.extractUsername(token);
        assertEquals(USERNAME, username);
    }

    @Test
    void testExtractExpiration() {
        String token = jwtUtil.generateToken(USERNAME);
        Date expirationDate = jwtUtil.extractExpiration(token);
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void testIsTokenExpired() {
        String token = jwtUtil.generateToken(USERNAME);
        // Appel de la méthode privée via la réflexion
        try {
            var method = JwtUtil.class.getDeclaredMethod("isTokenExpired", String.class);
            method.setAccessible(true);
            boolean isExpired = (boolean) method.invoke(jwtUtil, token);
            assertFalse(isExpired);
        } catch (Exception e) {
            fail("Erreur lors de l'appel à isTokenExpired : " + e.getMessage());
        }
    }
}