package org.example.chargingstationvalidationservice.repositories;

import org.example.chargingstationvalidationservice.models.Connector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectorRepository extends JpaRepository<Connector, String> {
}
