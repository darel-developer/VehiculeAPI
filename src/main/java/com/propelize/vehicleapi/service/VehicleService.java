package com.propelize.vehicleapi.service;

import aj.org.objectweb.asm.commons.Remapper;
import com.propelize.vehicleapi.model.Vehicle;
import com.propelize.vehicleapi.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getVehiclesByPrice(int price) {
        return vehicleRepository.findByPrice(price);
    }

    public List<Vehicle> getVehiculesByYear(int year) {
        return vehicleRepository.findByYear(year);
    }

    // Créer ou mettre à jour un véhicule
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // Récupérer tous les véhicules
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Récupérer un véhicule par ID
    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    // Supprimer un véhicule
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public void setVehicleRepository(VehicleRepository repo) {
        this.vehicleRepository = repo;
    }
}
