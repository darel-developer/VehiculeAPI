package com.propelize.vehicleapi.service;

import com.propelize.vehicleapi.model.User;
import com.propelize.vehicleapi.repository.UserRepository;
import com.propelize.vehicleapi.dto.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        if (userRepository.findByNameIgnoreCase(user.getName()).isPresent()) {
            throw new RuntimeException("Ce nom d'utilisateur existe déjà.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String authenticate(AuthRequest authRequest) {
        Optional<User> userOpt = userRepository.findByName(authRequest.getName());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                return jwtUtil.generateToken(user.getName());
            }
        }
        throw new RuntimeException("Identifiants invalides");
    }

    public void setUserRepository(UserRepository repo) {
        this.userRepository = repo;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }
}