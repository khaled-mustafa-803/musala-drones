package com.khaled.musala.drones.service;

import com.khaled.musala.drones.dto.DroneResponse;
import com.khaled.musala.drones.dto.MedicationResponse;
import com.khaled.musala.drones.dto.RegisterDroneRequest;
import com.khaled.musala.drones.entity.Drone;
import com.khaled.musala.drones.entity.Medication;
import com.khaled.musala.drones.repository.DroneRepository;
import com.khaled.musala.drones.repository.MedicationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class DroneServiceTest {

    @MockBean
    DroneRepository droneRepository;

    @MockBean
    MedicationRepository medicationRepository;

    @Autowired
    DroneService droneService;


    @Test
    public void registerDroneTest() {
        RegisterDroneRequest registerDroneRequest = RegisterDroneRequest.builder()
                .model(Drone.Model.Cruiserweight)
                .serialNumber("Serial")
                .weightLimit(400).build();
        DroneResponse response = droneService.registerDrone(registerDroneRequest);
        Assertions.assertEquals("Serial", response.getSerialNumber());
    }

    @Test
    public void registerDroneWithExistingSerialNumberTest_throwsException() {
        RegisterDroneRequest registerDroneRequest = new RegisterDroneRequest();
        registerDroneRequest.setSerialNumber("Serial");
        Mockito.when(droneRepository.findBySerialNumber(registerDroneRequest.getSerialNumber())).thenReturn(Optional.of(new Drone()));
        Assertions.assertThrows(ResponseStatusException.class, () -> droneService.registerDrone(registerDroneRequest));
    }

    @Test
    public void loadDroneWithMedicationTest() {

        Long medicationId = 2L;
        Long droneId = 1L;
        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.of(buildIdleDrone(droneId)));
        Mockito.when(medicationRepository.findAllByIdIn(List.of(medicationId))).thenReturn(List.of(buildMedication(medicationId)));

        DroneResponse droneResponse = droneService.loadDroneWithMedication(droneId, List.of(medicationId));
        Assertions.assertEquals(List.of(medicationId), droneResponse.getMedications().stream().map(MedicationResponse::getId).collect(Collectors.toList()));
        Assertions.assertEquals(Drone.State.LOADING, droneResponse.getState());
    }

    @Test
    public void loadDroneWithMedicationWithNonExistentDroneIdTest_throwsException() {

        Long medicationId = 2L;
        Long droneId = 1L;
        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> droneService.loadDroneWithMedication(droneId, List.of(medicationId)), "Drone ID does not exist");
    }

    @Test
    public void loadDroneWithMedicationWithNonExistentMedicationIdTest_throwsException() {

        Long medicationId = 2L;
        Long droneId = 1L;
        Mockito.when(medicationRepository.findAllByIdIn(List.of(medicationId))).thenReturn(new ArrayList<>());

        Assertions.assertThrows(ResponseStatusException.class, () -> droneService.loadDroneWithMedication(droneId, List.of(medicationId)), "Medication IDs:" + List.of(medicationId) + "do not exist");
    }

    @Test
    public void loadDroneWithMedicationWithNotIdleDroneTest_throwsException() {

        Long medicationId = 2L;
        Long droneId = 1L;
        Drone drone = buildIdleDrone(droneId);
        drone.setState(Drone.State.LOADED);
        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        Mockito.when(medicationRepository.findAllByIdIn(List.of(medicationId))).thenReturn(List.of(buildMedication(medicationId)));

        Assertions.assertThrows(ResponseStatusException.class, () -> droneService.loadDroneWithMedication(droneId, List.of(medicationId)), "Drone state is not IDLE");
    }

    @Test
    public void loadDroneWithMedicationWithLowBatteryDroneTest_throwsException() {

        Long medicationId = 2L;
        Long droneId = 1L;
        Drone drone = buildIdleDrone(droneId);
        drone.setBatteryCapacity(10);
        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        Mockito.when(medicationRepository.findAllByIdIn(List.of(medicationId))).thenReturn(List.of(buildMedication(medicationId)));

        Assertions.assertThrows(ResponseStatusException.class, () -> droneService.loadDroneWithMedication(droneId, List.of(medicationId)), "Drone battery capacity is low: " + drone.getBatteryCapacity());
    }

    @Test
    public void loadDroneWithMedicationWithLowAllowedWeightDroneTest_throwsException() {

        Long medicationId = 2L;
        Long droneId = 1L;
        Drone drone = buildIdleDrone(droneId);
        drone.setWeightLimit(0);
        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        Mockito.when(medicationRepository.findAllByIdIn(List.of(medicationId))).thenReturn(List.of(buildMedication(medicationId)));

        Assertions.assertThrows(ResponseStatusException.class, () -> droneService.loadDroneWithMedication(droneId, List.of(medicationId)), "Medications total weight:" + 100 + " exceeds drone's limit:" + drone.getWeightLimit());
    }

    @Test
    public void listDroneMedicationsTest() {
        Long medicationId = 2L;
        Long droneId = 1L;
        Drone drone = buildIdleDrone(droneId);
        drone.setMedications(List.of(buildMedication(medicationId)));
        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        List<MedicationResponse> medicationResponses = droneService.listDroneMedications(droneId);
        Assertions.assertEquals(1, medicationResponses.size());
        Assertions.assertEquals(medicationId, medicationResponses.get(0).getId());
    }

    @Test
    public void listDroneMedicationsWithNotExistingDroneTest_throwsException() {
        Long droneId = 1L;
        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResponseStatusException.class, () -> droneService.listDroneMedications(droneId), "Drone ID does not exist");
    }

    private Drone buildIdleDrone(Long droneId) {
        return Drone.builder()
                .id(droneId)
                .model(Drone.Model.Heavyweight)
                .serialNumber("Serial")
                .weightLimit(500)
                .batteryCapacity(100)
                .state(Drone.State.IDLE)
                .build();
    }

    private Medication buildMedication(Long medicationId) {
        return Medication.builder()
                .id(medicationId)
                .name("name")
                .weight(100)
                .image("image")
                .code("code")
                .build();
    }
}
