package org.example.chargingstationvalidationservice.repositories;

import org.example.chargingstationvalidationservice.models.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStation, String> {
}
