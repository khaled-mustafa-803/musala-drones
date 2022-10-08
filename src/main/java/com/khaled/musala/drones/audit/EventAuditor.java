package com.khaled.musala.drones.audit;

import com.khaled.musala.drones.repository.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EventAuditor {

    @Autowired
    DroneRepository droneRepository;

    Logger logger = LoggerFactory.getLogger(EventAuditor.class);
    @Scheduled(fixedRateString = "${audit-job-rate-milliseconds}")
    public void scheduledAudit(){
        String message = "Current drone battery capacity: \n" +
                droneRepository.findAll().stream().map(drone -> "Drone ID:" + drone.getId() + " , battery capacity:" + drone.getBatteryCapacity()).collect(Collectors.joining("\n"));
        logger.info(message);
    }
}
