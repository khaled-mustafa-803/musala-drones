package com.khaled.musala.drones.mapper;

import com.khaled.musala.drones.dto.MedicationResponse;
import com.khaled.musala.drones.entity.Medication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MedicationMapperTest {
    @Test
    public void mapMedicationEntityToMedicationResponseTest() {
        Medication medication = Medication.builder()
                .id(1L)
                .code("code")
                .image("image")
                .weight(100)
                .name("name")
                .build();
        MedicationResponse medicationResponse = MedicationMapper.mapMedicationEntityToMedicationResponse(medication);
        Assertions.assertEquals(medication.getId(), medicationResponse.getId());
        Assertions.assertEquals(medication.getCode(), medicationResponse.getCode());
        Assertions.assertEquals(medication.getImage(), medicationResponse.getImage());
        Assertions.assertEquals(medication.getWeight(), medicationResponse.getWeight());
        Assertions.assertEquals(medication.getName(), medicationResponse.getName());
    }

}
