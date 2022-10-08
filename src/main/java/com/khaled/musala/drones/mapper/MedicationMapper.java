package com.khaled.musala.drones.mapper;

import com.khaled.musala.drones.dto.MedicationResponse;
import com.khaled.musala.drones.entity.Medication;

public interface MedicationMapper {
    static MedicationResponse mapMedicationEntityToMedicationResponse(Medication medication) {
        return MedicationResponse.builder()
                .id(medication.getId())
                .name(medication.getName())
                .code(medication.getCode())
                .image(medication.getImage())
                .weight(medication.getWeight())
                .build();
    }
}
