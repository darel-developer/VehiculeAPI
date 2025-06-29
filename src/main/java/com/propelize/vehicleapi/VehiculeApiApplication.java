package com.propelize.vehicleapi;

import com.propelize.vehicleapi.model.User;
import com.propelize.vehicleapi.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VehiculeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehiculeApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDefaultUser(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByName("admin").isEmpty()) {
                User user = new User();
                user.setName("admin");
                user.setPassword("admin123"); // En production, encode le mot de passe !
                userRepository.save(user);
                System.out.println("Utilisateur admin/admin123 créé.");
            }
        };
    }
}
