package com.khaled.musala.drones;

import com.khaled.musala.drones.entity.Drone;
import com.khaled.musala.drones.entity.Medication;
import com.khaled.musala.drones.repository.DroneRepository;
import com.khaled.musala.drones.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Random;

@EnableScheduling
@SpringBootApplication
public class DronesApplication {

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    DroneRepository droneRepository;

    public static void main(String[] args) {
        SpringApplication.run(DronesApplication.class, args);
    }

    @PostConstruct
    public void populateDatabase() {
        for (int i = 1; i <= 10; i++) {
            medicationRepository.save(Medication.builder()
                    .code("code" + i)
                    .image("image" + i)
                    .weight(100 + i)
                    .name("medication " + i)
                    .build());
            droneRepository.save(Drone.builder()
                    .state(Drone.State.IDLE)
                    .batteryCapacity(new Random().nextInt(100))
                    .weightLimit(new Random().nextInt(500))
                    .serialNumber("SerialNumber " + i)
                    .model(Drone.Model.values()[new Random().nextInt(4)])
                    .build());
        }
    }

}
