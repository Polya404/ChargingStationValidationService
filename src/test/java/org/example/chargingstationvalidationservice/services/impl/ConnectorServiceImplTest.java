package org.example.chargingstationvalidationservice.services.impl;

import org.example.chargingstationvalidationservice.models.ChargingStation;
import org.example.chargingstationvalidationservice.models.Connector;
import org.example.chargingstationvalidationservice.repositories.ConnectorRepository;
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
class ConnectorServiceImplTest {
    @Mock
    private ConnectorRepository connectorRepository;
    @InjectMocks
    private ConnectorServiceImpl connectorService;

    @Test
    void addConnectors() {
        Connector connector = new Connector(UUID.randomUUID().toString(), "type1", 90,
                new ChargingStation(UUID.randomUUID().toString(), "test", "test", "test@mail.com",
                        "46.430095, 30.696315", true, new ArrayList<>()));
        connectorService.addConnectors(List.of(connector));
        verify(connectorRepository, times(1)).saveAll(List.of(connector));
    }
}