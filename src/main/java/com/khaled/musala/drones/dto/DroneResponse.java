package com.khaled.musala.drones.dto;

import com.khaled.musala.drones.entity.Drone;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneResponse {
    private Long id;
    private String serialNumber;
    private Drone.Model model;
    private Integer weightLimit;
    private Integer batteryCapacity;
    private Drone.State state;
}
