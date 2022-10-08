package com.khaled.musala.drones.mapper;

import com.khaled.musala.drones.dto.DroneResponse;
import com.khaled.musala.drones.dto.RegisterDroneRequest;
import com.khaled.musala.drones.entity.Drone;

public interface DroneMapper {
    static Drone mapRegisterRequestToDroneEntity(RegisterDroneRequest registerDroneRequest){
        return Drone.builder()
                .serialNumber(registerDroneRequest.getSerialNumber())
                .weightLimit(registerDroneRequest.getWeightLimit())
                .model(registerDroneRequest.getModel())
                .batteryCapacity(100)
                .state(Drone.State.IDLE)
                .build();
    }

    static DroneResponse mapDroneToDroneResponse(Drone drone){
        return DroneResponse.builder()
                .id(drone.getId())
                .batteryCapacity(drone.getBatteryCapacity())
                .model(drone.getModel())
                .serialNumber(drone.getSerialNumber())
                .state(drone.getState())
                .weightLimit(drone.getWeightLimit())
                .build();
    }
}
