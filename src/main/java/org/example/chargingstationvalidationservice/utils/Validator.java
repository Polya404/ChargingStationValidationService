package org.example.chargingstationvalidationservice.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.EnumUtils;
import org.example.chargingstationvalidationservice.models.ChargingStation;
import org.example.chargingstationvalidationservice.models.Connector;
import org.example.chargingstationvalidationservice.models.ConnectorType;
import org.example.chargingstationvalidationservice.models.Response;

import java.util.List;
import java.util.regex.Pattern;

@UtilityClass
public class Validator {
    Pattern UUID_REGEX =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    Pattern ADDRESS_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    Pattern COORDINATE_REGEX = Pattern.compile("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$");

    public void validateDataFromRequest(ChargingStation chargingStation, Response response) {
        if (!requiredFieldIfPublic(chargingStation)){
            response.setMessage(new StringBuilder(Messages.getMessage("error.blank_field_for_public_station")));
        }
        if (!idIsValid(chargingStation.getId())) {
            response.setMessage(new StringBuilder(Messages.getMessage("error.incorrect_id")));
        }
        connectorValidation(chargingStation.getConnectors(), response, chargingStation);
        if (!tittleIsValid(chargingStation)) {
            response.setMessage(new StringBuilder(Messages.getMessage("error.empty_tittle")));
        }
        if (!descriptionIsValid(chargingStation)) {
            response.setMessage(new StringBuilder(Messages.getMessage("error.empty_description")));
        }
        if (!addressIsValid(chargingStation)) {
            response.setMessage(new StringBuilder(Messages.getMessage("error.address_incorrect")));
        }
        if (!geoCoordinatesIsValid(chargingStation)) {
            response.setMessage(new StringBuilder(Messages.getMessage("error.coordinates_incorrect")));
        }
    }

    public boolean requiredFieldIfPublic(ChargingStation chargingStation) {
        if (chargingStation.getIsPublic()) {
            if (chargingStation.getAddress() == null || chargingStation.getAddress().isEmpty()) {
                return false;
            }
            return chargingStation.getGeoCoordinates() != null && !chargingStation.getGeoCoordinates().isEmpty();
        }
        return true;
    }

    public boolean idIsValid(String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        return UUID_REGEX.matcher(id).matches();
    }

    public boolean tittleIsValid(ChargingStation chargingStation) {
        if (chargingStation.getIsPublic()) {
            return chargingStation.getTitle() != null && !chargingStation.getTitle().isEmpty();
        }
        return true;
    }

    public boolean descriptionIsValid(ChargingStation chargingStation) {
        if (chargingStation.getIsPublic()) {
            return chargingStation.getDescription() != null && !chargingStation.getDescription().isEmpty();
        }
        return true;
    }

    public boolean addressIsValid(ChargingStation chargingStation) {
        if (chargingStation.getAddress() == null || chargingStation.getAddress().isEmpty()) {
            return true;
        }
        return ADDRESS_REGEX.matcher(chargingStation.getAddress()).matches();
    }

    public boolean geoCoordinatesIsValid(ChargingStation chargingStation) {
        if (chargingStation.getGeoCoordinates() == null || chargingStation.getGeoCoordinates().isEmpty()) {
            return true;
        }
        return COORDINATE_REGEX.matcher(chargingStation.getGeoCoordinates()).matches();
    }

    public boolean connectorTypeIsValid(String type) {
        try {
            return EnumUtils.isValidEnumIgnoreCase(ConnectorType.class, type);
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    public boolean connectorMaxPowerKWIsValid(int maxPower) {
        return 0 < maxPower && maxPower < 1000000;
    }

    private void connectorValidation(List<Connector> connectors, Response response, ChargingStation station) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Connector connector : connectors) {
            if (!(Validator.idIsValid(connector.getId()))) {
                stringBuilder.append(String.format(Messages
                                .getMessage("error.connector_id_incorrect"), station.getId(),
                        connector.getId()));
            }
            if (!Validator.connectorTypeIsValid(connector.getType())) {
                stringBuilder.append(String.format(Messages
                                .getMessage("error.connector_type_incorrect"), station.getId(),
                        connector.getType(), connector.getId()));
            }
            if (!Validator.connectorMaxPowerKWIsValid(connector.getMaxPowerKw())) {
                stringBuilder.append(String.format(Messages
                                .getMessage("error.connector_max_power_incorrect"), station.getId(),
                        connector.getMaxPowerKw(), connector.getId()));
            }
        }
        response.setMessage(stringBuilder);
    }
}
