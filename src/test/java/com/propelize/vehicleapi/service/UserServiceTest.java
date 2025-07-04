package com.propelize.vehicleapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.propelize.vehicleapi.model.User;
import com.propelize.vehicleapi.repository.UserRepository;

import java.util.Optional;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setName("sampleUser");
        user.setPassword("samplePassword");
    }

    @Test
    public void testFindUserById() {
        when(userRepository.findById("12345")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById("12345");

        assertTrue(foundUser.isPresent());
        assertEquals("sampleUser", foundUser.get().getName());
    }

    @Test
    public void testSaveUser() {
        // Mock pour éviter l'exception de doublon
        when(userRepository.findByNameIgnoreCase(user.getName())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("sampleUser", savedUser.getName());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById("12345");

        userService.deleteUser("12345");

        verify(userRepository, times(1)).deleteById("12345");
    }
}