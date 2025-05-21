package com.propelize.vehicleapi.service;

import com.propelize.vehicleapi.model.User;
import com.propelize.vehicleapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Créer ou mettre à jour un utilisateur
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void setUserRepository(UserRepository repo) {
        this.userRepository = repo;
    }

    // Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Récupérer un utilisateur par ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Supprimer un utilisateur
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}