package com.khaled.musala.drones.service;

import com.khaled.musala.drones.dto.DroneResponse;
import com.khaled.musala.drones.dto.RegisterDroneRequest;
import com.khaled.musala.drones.entity.Drone;
import com.khaled.musala.drones.entity.Medication;
import com.khaled.musala.drones.mapper.DroneMapper;
import com.khaled.musala.drones.repository.DroneRepository;
import com.khaled.musala.drones.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DroneService {

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    MedicationRepository medicationRepository;

    public DroneResponse registerDrone(@NotNull RegisterDroneRequest registerDroneRequest) {
        if (droneRepository.findBySerialNumber(registerDroneRequest.getSerialNumber()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Serial number already exists");
        }
        Drone drone = DroneMapper.mapRegisterRequestToDroneEntity(registerDroneRequest);
        droneRepository.save(drone);
        return DroneMapper.mapDroneToDroneResponse(drone);
    }


    public DroneResponse loadDroneWithMedication(Long id, @NotNull List<Long> medicationIds) {
        Drone drone = droneRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone ID does not exist"));
        List<Medication> medications = medicationRepository.findAllByIdIn(medicationIds);
        if (medications.size() != medicationIds.size()) {
            List<Long> ids = medications.stream().map(Medication::getId).filter(medicationIds::contains).collect(Collectors.toList());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication IDs:" + ids + "do not exist");
        }
        validateDroneCanLoadMedications(drone, medications);
        drone.setMedications(medications);
        droneRepository.save(drone);
        return DroneMapper.mapDroneToDroneResponse(drone);
    }

    private void validateDroneCanLoadMedications(Drone drone, List<Medication> medications) {
        if (!Drone.State.IDLE.equals(drone.getState())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Drone state is not IDLE");
        }
        if (drone.getBatteryCapacity() < 25) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Drone battery capacity is low: " + drone.getBatteryCapacity());
        }
        Integer medicationsWeight = medications.stream().map(Medication::getWeight).reduce(0, Integer::sum);
        if (medicationsWeight > drone.getWeightLimit()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medications total weight:" + medicationsWeight + " exceeds drone's limit:" + drone.getWeightLimit());
        }
    }


}
