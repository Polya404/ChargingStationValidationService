package org.example.chargingstationvalidationservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.chargingstationvalidationservice.models.ChargingStation;
import org.example.chargingstationvalidationservice.repositories.ChargingStationRepository;
import org.example.chargingstationvalidationservice.services.ChargingStationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChargingStationServiceImpl implements ChargingStationService {
    private final ChargingStationRepository chargingStationRepository;
    @Override
    public void addChargingStation(ChargingStation chargingStation) {
        chargingStationRepository.save(chargingStation);
    }
}
