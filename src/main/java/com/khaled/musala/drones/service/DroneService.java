package com.khaled.musala.drones.service;

import com.khaled.musala.drones.dto.DroneResponse;
import com.khaled.musala.drones.dto.RegisterDroneRequest;
import com.khaled.musala.drones.entity.Drone;
import com.khaled.musala.drones.mapper.DroneMapper;
import com.khaled.musala.drones.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DroneService {

    @Autowired
    DroneRepository droneRepository;

    public DroneResponse registerDrone(RegisterDroneRequest registerDroneRequest) {
        if (droneRepository.findBySerialNumber(registerDroneRequest.getSerialNumber()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Serial number already exists");
        }
        Drone drone = DroneMapper.mapRegisterRequestToDroneEntity(registerDroneRequest);
        droneRepository.save(drone);
        return DroneMapper.mapDroneToDroneResponse(drone);
    }


}
