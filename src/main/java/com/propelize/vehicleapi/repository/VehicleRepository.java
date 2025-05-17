package com.propelize.vehicleapi.repository;

import com.propelize.vehicleapi.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByPrice(int price);
    List<Vehicle> findByYear(int year);
    // - findAll()
    // - findById(id)
    // - save(vehicle)
    // - deleteById(id)
}
