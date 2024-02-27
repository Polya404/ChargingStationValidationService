package org.example.chargingstationvalidationservice.services.impl;

import org.example.chargingstationvalidationservice.models.ChargingStation;
import org.example.chargingstationvalidationservice.models.Connector;
import org.example.chargingstationvalidationservice.repositories.ChargingStationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChargingStationServiceImplTest {
    @Mock
    private ChargingStationRepository chargingStationRepository;

    @InjectMocks
    private ChargingStationServiceImpl stationService;

    @Test
    void addChargingStation() {
        ChargingStation chargingStation = new ChargingStation();
        List<Connector> connectors = List.of(new Connector(UUID.randomUUID().toString(), "CCS", 30, chargingStation));
        chargingStation.setId(UUID.randomUUID().toString());
        chargingStation.setAddress("test@mail.com");
        chargingStation.setTitle("Something");
        chargingStation.setDescription("Description");
        chargingStation.setIsPublic(true);
        chargingStation.setGeoCoordinates("46.430095, 30.696315");
        chargingStation.setConnectors(connectors);

        stationService.addChargingStation(chargingStation);

        verify(chargingStationRepository, times(1)).save(chargingStation);

    }

    @Test
    public void testErrorSavingChargingStation() {
        ChargingStation chargingStation = new ChargingStation();
        doThrow(new RuntimeException("Error saving charging station")).when(chargingStationRepository).save(chargingStation);
        assertThrows(RuntimeException.class, () -> stationService.addChargingStation(chargingStation));
        verify(chargingStationRepository, times(1)).save(chargingStation);
    }
}