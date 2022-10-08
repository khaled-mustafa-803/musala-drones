package com.khaled.musala.drones.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationResponse {
    private Long id;
    private String name;
    private Integer weight;
    private String code;
    private String image;
}
