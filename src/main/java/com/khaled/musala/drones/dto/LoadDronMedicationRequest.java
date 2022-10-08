package com.khaled.musala.drones.dto;

import com.khaled.musala.drones.entity.Drone;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadDronMedicationRequest {
    @NotNull(message = "Serial number is mandatory")
    private String serialNumber;
    @NotNull(message = "Serial number is mandatory")
    private Drone.Model model;
    @Positive(message = "Weight limit must be larger than zero")
    @Max(value = 500, message = "Weight limit max size is 500 grams")
    private Integer weightLimit;
}
