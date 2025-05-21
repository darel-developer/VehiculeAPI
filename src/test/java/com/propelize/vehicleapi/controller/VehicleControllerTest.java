package com.propelize.vehicleapi.controller;

import com.propelize.vehicleapi.model.Vehicle;
import com.propelize.vehicleapi.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVehicles() {
        Vehicle vehicle1 = new Vehicle("Toyota", "Corolla", "Car", "ABC123", 2020, 15000);
        Vehicle vehicle2 = new Vehicle("Ford", "F-150", "Truck", "XYZ789", 2019, 25000);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

        when(vehicleService.getAllVehicles()).thenReturn(vehicles);

        List<Vehicle> response = vehicleController.getAllVehicles();

        assertEquals(vehicles, response);
        verify(vehicleService, times(1)).getAllVehicles();
    }

    @Test
    void testCreateVehicle() {
        Vehicle vehicle = new Vehicle("Toyota", "Corolla", "Car", "ABC123", 2020, 15000);

        when(vehicleService.saveVehicle(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle response = vehicleController.createVehicle(vehicle);

        assertEquals(vehicle, response);
        verify(vehicleService, times(1)).saveVehicle(any(Vehicle.class));
    }

    @Test
    void testUpdateVehicle_Found() {
        Long id = 1L;
        Vehicle existing = new Vehicle("Toyota", "Corolla", "Car", "ABC123", 2020, 15000);
        Vehicle updated = new Vehicle("Toyota", "Yaris", "Car", "ABC123", 2021, 16000);

        when(vehicleService.getVehicleById(id)).thenReturn(Optional.of(existing));
        when(vehicleService.saveVehicle(any(Vehicle.class))).thenReturn(updated);

        var response = vehicleController.updateVehicle(id, updated);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
        verify(vehicleService).getVehicleById(id);
        verify(vehicleService).saveVehicle(any(Vehicle.class));
    }

    @Test
    void testUpdateVehicle_NotFound() {
        Long id = 1L;
        Vehicle updated = new Vehicle("Toyota", "Yaris", "Car", "ABC123", 2021, 16000);

        when(vehicleService.getVehicleById(id)).thenReturn(Optional.empty());

        var response = vehicleController.updateVehicle(id, updated);

        assertEquals(404, response.getStatusCodeValue());
        verify(vehicleService).getVehicleById(id);
        verify(vehicleService, never()).saveVehicle(any(Vehicle.class));
    }

    @Test
    void testDeleteVehicle() {
        doNothing().when(vehicleService).deleteVehicle(anyLong());

        var response = vehicleController.deleteVehicle(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(vehicleService, times(1)).deleteVehicle(anyLong());
    }
}