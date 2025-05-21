package com.propelize.vehicleapi.seeder;

import com.propelize.vehicleapi.model.Vehicle;
import com.propelize.vehicleapi.model.User;
import com.propelize.vehicleapi.repository.VehicleRepository;
import com.propelize.vehicleapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si la table "vehicles" est vide
        if (vehicleRepository.count() == 0) {
            List<Vehicle> vehicles = List.of(
                new Vehicle("Toyota", "Corolla", "car", "AB123CD", 2020, 20000),
                new Vehicle("Peugeot", "Partner", "van", "CD456EF", 2019, 25000),
                new Vehicle("Hyundai", "Elantra", "car", "GH789IJ", 2021, 22000),
                new Vehicle("Mercedes", "Sprinter", "van", "KL012MN", 2018, 30000)
            );
            vehicleRepository.saveAll(vehicles);
        }

        // Vérifier si la table "users" est vide
        if (userRepository.count() == 0) {
            List<User> users = List.of(
                new User("admin", "admin123"),
                new User("user1", "password1"),
                new User("user2", "password2")
            );
            userRepository.saveAll(users);
        }
    }
}