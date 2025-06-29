package com.propelize.vehicleapi.repository;

import com.propelize.vehicleapi.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    List<Vehicle> findByPrice(int price);
    List<Vehicle> findByYear(int year);
}