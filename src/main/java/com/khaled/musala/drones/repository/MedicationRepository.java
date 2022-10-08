package com.khaled.musala.drones.repository;

import com.khaled.musala.drones.entity.Medication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Long> {
    List<Medication> findAllByIdIn(List<Long> ids);

}
