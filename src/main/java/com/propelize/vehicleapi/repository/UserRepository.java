package com.propelize.vehicleapi.repository;

import com.propelize.vehicleapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Trouver un utilisateur par son nom
    Optional<User> findByName(String name);
    Optional<User> findByNameIgnoreCase(String name);
}