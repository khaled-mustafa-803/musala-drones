package com.khaled.musala.drones.repository;

import com.khaled.musala.drones.entity.Drone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends CrudRepository<Drone, Long> {
    Optional<Drone> findBySerialNumber(String serialNumber);
    List<Drone> findAllByStateAndBatteryCapacityGreaterThan(Drone.State state, Integer batteryCapacity);
}
