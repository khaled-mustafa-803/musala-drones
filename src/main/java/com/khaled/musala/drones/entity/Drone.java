package com.khaled.musala.drones.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serialNumber;
    @Enumerated(value = EnumType.STRING)
    private Model model;
    private Integer weightLimit;
    private Integer batteryCapacity;
    @Enumerated(value = EnumType.STRING)
    private State state;

    @Builder.Default
    @ManyToMany
    private List<Medication> medications = new ArrayList<>();

    public enum Model {
        Lightweight, Middleweight, Cruiserweight, Heavyweight
    }

    public enum State {
        IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING
    }
}
