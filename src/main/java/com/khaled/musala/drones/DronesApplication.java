package com.khaled.musala.drones;

import com.khaled.musala.drones.entity.Medication;
import com.khaled.musala.drones.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@EnableScheduling
@SpringBootApplication
public class DronesApplication {

    @Autowired
    MedicationRepository medicationRepository;

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
        }
    }

}
