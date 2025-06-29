package com.propelize.vehicleapi.controller;

import com.propelize.vehicleapi.model.Vehicle;
import com.propelize.vehicleapi.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.saveVehicle(vehicle);
    }

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable String id) {
        return vehicleService.getVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/price/{price}")
    public ResponseEntity<List<Vehicle>> getVehiclesByPrice(@PathVariable int price) {
        return ResponseEntity.ok(vehicleService.getVehiclesByPrice(price));
    }

    @GetMapping("/search/year/{year}")
    public ResponseEntity<List<Vehicle>> getVehicleByYear(@PathVariable int year) {
        return ResponseEntity.ok(vehicleService.getVehiculesByYear(year));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable String id, @RequestBody Vehicle updatedVehicle) {
        return vehicleService.getVehicleById(id).map(vehicle -> {
            vehicle.setBrand(updatedVehicle.getBrand());
            vehicle.setModel(updatedVehicle.getModel());
            vehicle.setType(updatedVehicle.getType());
            vehicle.setPlateNumber(updatedVehicle.getPlateNumber());
            vehicle.setYear(updatedVehicle.getYear());
            vehicle.setPrice(updatedVehicle.getPrice());
            return ResponseEntity.ok(vehicleService.saveVehicle(vehicle));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}