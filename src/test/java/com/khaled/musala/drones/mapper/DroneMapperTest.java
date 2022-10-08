package com.khaled.musala.drones.mapper;

import com.khaled.musala.drones.dto.DroneResponse;
import com.khaled.musala.drones.dto.RegisterDroneRequest;
import com.khaled.musala.drones.entity.Drone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DroneMapperTest {
    @Test
    public void mapRegisterRequestToDroneEntityTest(){
        RegisterDroneRequest request = RegisterDroneRequest.builder()
                .weightLimit(400)
                .serialNumber("Serial number")
                .model(Drone.Model.Heavyweight).build();
        Drone drone = DroneMapper.mapRegisterRequestToDroneEntity(request);
        Assertions.assertEquals(request.getSerialNumber(), drone.getSerialNumber());
        Assertions.assertEquals(request.getModel(), drone.getModel());
        Assertions.assertEquals(request.getWeightLimit(), drone.getWeightLimit());
        Assertions.assertEquals(100, drone.getBatteryCapacity());
        Assertions.assertEquals(Drone.State.IDLE, drone.getState());
    }

    @Test
    public void mapDroneToDroneResponseTest(){
        Drone drone = Drone.builder()
                .id(1L)
                .state(Drone.State.IDLE)
                .batteryCapacity(90)
                .weightLimit(400)
                .serialNumber("Serial")
                .model(Drone.Model.Cruiserweight)
                .build();
        DroneResponse droneResponse = DroneMapper.mapDroneToDroneResponse(drone);
        Assertions.assertEquals(drone.getId(), droneResponse.getId());
        Assertions.assertEquals(drone.getState(), droneResponse.getState());
        Assertions.assertEquals(drone.getBatteryCapacity(), droneResponse.getBatteryCapacity());
        Assertions.assertEquals(drone.getWeightLimit(), droneResponse.getWeightLimit());
        Assertions.assertEquals(drone.getSerialNumber(), droneResponse.getSerialNumber());
        Assertions.assertEquals(drone.getModel(), droneResponse.getModel());

    }
}
