package org.example.chargingstationvalidationservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.chargingstationvalidationservice.models.Connector;
import org.example.chargingstationvalidationservice.repositories.ConnectorRepository;
import org.example.chargingstationvalidationservice.services.ConnectorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectorServiceImpl implements ConnectorService {
    private final ConnectorRepository connectorRepository;
    @Override
    public void addConnectors(List<Connector> connectors) {
        connectorRepository.saveAll(connectors);
    }
}
