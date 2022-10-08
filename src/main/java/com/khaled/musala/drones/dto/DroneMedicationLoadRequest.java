package com.khaled.musala.drones.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class DroneMedicationLoadRequest {
    private List<@NotNull Long> medicationIds;
}
