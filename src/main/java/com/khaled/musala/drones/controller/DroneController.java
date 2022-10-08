package com.khaled.musala.drones.controller;

import com.khaled.musala.drones.dto.DroneResponse;
import com.khaled.musala.drones.dto.RegisterDroneRequest;
import com.khaled.musala.drones.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/drones")
public class DroneController {

    @Autowired
    DroneService droneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DroneResponse registerDrone(@RequestBody @Valid RegisterDroneRequest registerDroneRequest){
        return droneService.registerDrone(registerDroneRequest);
    }
}
