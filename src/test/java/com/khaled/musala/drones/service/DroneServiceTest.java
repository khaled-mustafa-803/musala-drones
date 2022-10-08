package com.khaled.musala.drones.service;

import com.khaled.musala.drones.dto.DroneResponse;
import com.khaled.musala.drones.dto.RegisterDroneRequest;
import com.khaled.musala.drones.entity.Drone;
import com.khaled.musala.drones.repository.DroneRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@SpringBootTest
public class DroneServiceTest {

    @MockBean
    DroneRepository droneRepository;

    @Autowired
    DroneService droneService;

    RegisterDroneRequest registerDroneRequest;


    @BeforeEach
    public void setup(){
        registerDroneRequest = RegisterDroneRequest.builder()
                .model(Drone.Model.Cruiserweight)
                .serialNumber("Serial")
                .weightLimit(400).build();
    }

    @Test
    public void registerDroneTest(){
        DroneResponse response =  droneService.registerDrone(registerDroneRequest);
        Assertions.assertEquals("Serial", response.getSerialNumber());
    }

    @Test
    public void registerDroneWithExistingSerialNumber_throwsException(){
        Mockito.when(droneRepository.findBySerialNumber(registerDroneRequest.getSerialNumber())).thenReturn(Optional.of(new Drone()));
        Assertions.assertThrows(ResponseStatusException.class, ()-> droneService.registerDrone(registerDroneRequest));
    }
}
