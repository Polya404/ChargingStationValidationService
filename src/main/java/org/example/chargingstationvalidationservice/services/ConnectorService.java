package org.example.chargingstationvalidationservice.services;

import org.example.chargingstationvalidationservice.models.Connector;

import java.util.List;

public interface ConnectorService {
    void addConnectors(List<Connector> connectors);
}
