package com.khaled.musala.drones.repository;

import com.khaled.musala.drones.entity.Drone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends CrudRepository<Drone, Long> {
}
