package com.propelize.vehicleapi.service;

import com.propelize.vehicleapi.model.Vehicle;
import com.propelize.vehicleapi.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleServiceTest {

    private VehicleRepository vehicleRepository;
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        vehicleRepository = mock(VehicleRepository.class);
        vehicleService = new VehicleService();

        // Injection manuelle du mock dans le service
        // avec une classe interne ou setter public
        // => tu dois modifier VehicleService pour ajouter un setter
        vehicleService.setVehicleRepository(vehicleRepository);
    }


    @Test
    void testSaveVehicle() {
        Vehicle vehicle = new Vehicle("Toyota", "Yaris", "car", "123ABC", 2020, 15000);
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        Vehicle saved = vehicleService.saveVehicle(vehicle);

        assertNotNull(saved);
        assertEquals("Toyota", saved.getBrand());
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void testGetAllVehicles() {
        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle("Renault", "Clio", "car", "AA111BB", 2018, 10000),
                new Vehicle("Peugeot", "208", "car", "CC222DD", 2019, 12000)
        );

        when(vehicleRepository.findAll()).thenReturn(vehicles);

        List<Vehicle> result = vehicleService.getAllVehicles();

        assertEquals(2, result.size());
    }

    @Test
    void testGetVehicleById() {
        Vehicle vehicle = new Vehicle("Honda", "Civic", "car", "EE333FF", 2021, 18000);
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        Optional<Vehicle> found = vehicleService.getVehicleById(1L);

        assertTrue(found.isPresent());
        assertEquals("Civic", found.get().getModel());
    }

    @Test
    void testGetVehiclesByPrice() {
        List<Vehicle> vehicles = List.of(new Vehicle("Mazda", "3", "car", "FF444GG", 2022, 20000));
        when(vehicleRepository.findByPrice(20000)).thenReturn(vehicles);

        List<Vehicle> result = vehicleService.getVehiclesByPrice(20000);

        assertEquals(1, result.size());
        assertEquals("Mazda", result.get(0).getBrand());
    }
}
