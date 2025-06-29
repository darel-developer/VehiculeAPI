package com.propelize.vehicleapi.service;

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

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void setVehicleRepository(VehicleRepository repo) {
        this.vehicleRepository = repo;
    }

    public List<Vehicle> getVehiclesByPrice(int price) {
        return vehicleRepository.findByPrice(price);
    }

    public List<Vehicle> getVehiculesByYear(int year) {
        return vehicleRepository.findByYear(year);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(String id) {
        return vehicleRepository.findById(id);
    }

    public void deleteVehicle(String id) {
        vehicleRepository.deleteById(id);
    }
}