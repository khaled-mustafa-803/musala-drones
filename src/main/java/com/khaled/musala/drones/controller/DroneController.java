package com.khaled.musala.drones.controller;

import com.khaled.musala.drones.dto.DroneMedicationLoadRequest;
import com.khaled.musala.drones.dto.DroneResponse;
import com.khaled.musala.drones.dto.MedicationResponse;
import com.khaled.musala.drones.dto.RegisterDroneRequest;
import com.khaled.musala.drones.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/drones")
public class DroneController {

    @Autowired
    DroneService droneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DroneResponse registerDrone(@RequestBody @Valid RegisterDroneRequest registerDroneRequest) {
        return droneService.registerDrone(registerDroneRequest);
    }

    @PutMapping("/{id}/load")
    @ResponseStatus(HttpStatus.OK)
    public DroneResponse loadDroneWithMedication(@PathVariable("id") Long id, @RequestBody @Valid DroneMedicationLoadRequest droneMedicationLoadRequest) {
        return droneService.loadDroneWithMedication(id, droneMedicationLoadRequest.getMedicationIds());
    }

    @GetMapping("/{id}/medications")
    @ResponseStatus(HttpStatus.OK)
    public List<MedicationResponse> listDroneMedications(@PathVariable("id") Long id){
        return droneService.listDroneMedications(id);
    }

    @GetMapping("/{id}/battery")
    @ResponseStatus(HttpStatus.OK)
    public Integer checkDroneBatteryLevel(@PathVariable("id") Long id){
        return droneService.checkDroneBatteryLevel(id);
    }

}
