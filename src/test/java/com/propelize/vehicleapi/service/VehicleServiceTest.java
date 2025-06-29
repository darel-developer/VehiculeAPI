package com.propelize.vehicleapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.propelize.vehicleapi.model.Vehicle;
import com.propelize.vehicleapi.repository.VehicleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicle = new Vehicle("Toyota", "Camry", "Car", "ABC123", 2020, 24000);
    }

    @Test
    void testGetAllVehicles() {
        Vehicle vehicle1 = new Vehicle("Toyota", "Camry", "Car", "ABC123", 2020, 24000);
        Vehicle vehicle2 = new Vehicle("Ford", "F-150", "Truck", "XYZ789", 2019, 30000);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

        when(vehicleRepository.findAll()).thenReturn(vehicles);

        List<Vehicle> result = vehicleService.getAllVehicles();

        assertEquals(2, result.size());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testGetVehicleById() {
        when(vehicleRepository.findById("12345")).thenReturn(Optional.of(vehicle));

        Optional<Vehicle> result = vehicleService.getVehicleById("12345");

        assertTrue(result.isPresent());
        assertEquals("Toyota", result.get().getBrand());
        verify(vehicleRepository, times(1)).findById("12345");
    }

    @Test
    void testSaveVehicle() {
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);

        assertNotNull(savedVehicle);
        assertEquals("Toyota", savedVehicle.getBrand());
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void testDeleteVehicle() {
        String vehicleId = "12345";

        doNothing().when(vehicleRepository).deleteById(vehicleId);

        vehicleService.deleteVehicle(vehicleId);

        verify(vehicleRepository, times(1)).deleteById(vehicleId);
    }
}