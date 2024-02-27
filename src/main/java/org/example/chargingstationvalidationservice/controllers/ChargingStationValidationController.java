package org.example.chargingstationvalidationservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.chargingstationvalidationservice.models.ChargingStation;
import org.example.chargingstationvalidationservice.models.Connector;
import org.example.chargingstationvalidationservice.models.Response;
import org.example.chargingstationvalidationservice.services.ChargingStationService;
import org.example.chargingstationvalidationservice.services.ConnectorService;
import org.example.chargingstationvalidationservice.services.ResponseService;
import org.example.chargingstationvalidationservice.utils.Messages;
import org.example.chargingstationvalidationservice.utils.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChargingStationValidationController {
    private final ChargingStationService stationService;
    private final ConnectorService connectorService;
    private final ResponseService responseService;

    @PostMapping("/validate")
    public ResponseEntity<Response> validateChargingStation(@Valid @RequestBody ChargingStation chargingStation) {
        Response response = setDataToResponse(chargingStation);
        Validator.validateDataFromRequest(chargingStation, response);
        chargingStation.getConnectors().forEach(connector -> connector.setChargingStation(chargingStation));
        stationService.addChargingStation(chargingStation);
        connectorService.addConnectors(chargingStation.getConnectors());
        return response.getMessage().isEmpty() ? sendPositiveResponse(response)
                : ResponseEntity.ok(responseService.addResponse(response));
    }

    private Response setDataToResponse(ChargingStation chargingStation) {
        Response response = new Response();
        response.setId(UUID.randomUUID());
        response.setIdStation(chargingStation.getId());
        response.setIdConnectors(chargingStation.getConnectors().stream().map(Connector::getId).toList());
        return response;
    }

    private ResponseEntity<Response> sendPositiveResponse(Response response) {
        response.setMessage(new StringBuilder(Messages.getMessage("ok.correct_")));
        return ResponseEntity.ok(responseService.addResponse(response));
    }
}
