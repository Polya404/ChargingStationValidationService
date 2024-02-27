package org.example.chargingstationvalidationservice.utils;

import org.example.chargingstationvalidationservice.models.ChargingStation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;


class ValidatorTest {

    @Test
    void idIsValid() {
        String validId = UUID.randomUUID().toString();
        Assertions.assertTrue(Validator.idIsValid(validId));
    }

    @Test
    void idIsValidWithInvalidValue() {
        String invalidId = "123";
        Assertions.assertFalse(Validator.idIsValid(invalidId));
    }

    @Test
    void idIsValidWithEmptyValue() {
        String emptyId = "";
        Assertions.assertFalse(Validator.idIsValid(emptyId));
    }

    @Test
    void idIsValidWithNULL() {
        String nullId = null;
        Assertions.assertFalse(Validator.idIsValid(nullId));
    }

    @Test
    void tittleIsValid() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String validTittle = "Welcome";
        station.setTitle(validTittle);
        Assertions.assertTrue(Validator.tittleIsValid(station));
    }

    @Test
    void tittleIsValidWithNullValueAndPublicStation() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String nullTittle = null;
        station.setTitle(nullTittle);
        Assertions.assertFalse(Validator.tittleIsValid(station));
    }

    @Test
    void tittleIsValidWithNullValue() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(false);
        String nullTittle = null;
        station.setTitle(nullTittle);
        Assertions.assertTrue(Validator.tittleIsValid(station));
    }

    @Test
    void tittleIsValidWithEmptyValueAndPublicStation() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String emptyTittle = "";
        station.setTitle(emptyTittle);
        Assertions.assertFalse(Validator.tittleIsValid(station));
    }

    @Test
    void tittleIsValidWithEmptyValue() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(false);
        String emptyTittle = "";
        station.setTitle(emptyTittle);
        Assertions.assertTrue(Validator.tittleIsValid(station));
    }

    @Test
    void descriptionIsValid() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String validDescription = "Something";
        station.setDescription(validDescription);
        Assertions.assertTrue(Validator.descriptionIsValid(station));
    }

    @Test
    void descriptionIsValidWithNullValueAndPublicStation() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String nullDescription = null;
        station.setDescription(nullDescription);
        Assertions.assertFalse(Validator.descriptionIsValid(station));
    }

    @Test
    void descriptionIsValidWithNullValue() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(false);
        String nullDescription = null;
        station.setDescription(nullDescription);
        Assertions.assertTrue(Validator.descriptionIsValid(station));
    }

    @Test
    void descriptionIsValidWithEmptyValue() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(false);
        String emptyDescription = "";
        station.setDescription(emptyDescription);
        Assertions.assertTrue(Validator.descriptionIsValid(station));
    }

    @Test
    void descriptionIsValidWithEmptyValueAndPublicStation() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String emptyDescription = "";
        station.setDescription(emptyDescription);
        Assertions.assertFalse(Validator.descriptionIsValid(station));
    }

    @Test
    void addressIsValid() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String validAddress = "test@mail.com";
        station.setAddress(validAddress);
        Assertions.assertTrue(Validator.addressIsValid(station));
    }

    @Test
    void addressIsValidWithInvalidValue() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String invalidAddress = "Something";
        station.setAddress(invalidAddress);
        Assertions.assertFalse(Validator.addressIsValid(station));
    }

    @Test
    void addressIsValidWithEmptyValueAndPublicStation() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String emptyAddress = "";
        station.setAddress(emptyAddress);
        Assertions.assertFalse(Validator.requiredFieldIfPublic(station));
    }

    @Test
    void addressIsValidWithEmptyValue() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(false);
        String emptyAddress = "";
        station.setAddress(emptyAddress);
        Assertions.assertTrue(Validator.requiredFieldIfPublic(station));
    }

    @Test
    void addressIsValidWithNullValueAndPublicStation() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String nullAddress = null;
        station.setAddress(nullAddress);
        Assertions.assertFalse(Validator.requiredFieldIfPublic(station));
    }

    @Test
    void addressIsValidWithNullValue() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(false);
        String nullAddress = null;
        station.setAddress(nullAddress);
        Assertions.assertTrue(Validator.requiredFieldIfPublic(station));
    }

    @Test
    void geoCoordinatesIsValid() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        List<String> validCoordinates = List.of("+90.0, -127.554334", "45, 180", "-90, -180", "-90.000, -180.0000",
                "+90, +180", "47.1231231, 179.99999999", "50.4477, 30.5233");
        for (String value : validCoordinates) {
            station.setGeoCoordinates(value);
            Assertions.assertTrue(Validator.geoCoordinatesIsValid(station));
        }
    }

    @Test
    void geoCoordinatesIsValidWithInvalidValue() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        List<String> invalidCoordinates = List.of("-90., -180.", "+90.1, -100.111", "-91, 123.456", "045, 180");

        for (String value : invalidCoordinates) {
            station.setGeoCoordinates(value);
            Assertions.assertFalse(Validator.geoCoordinatesIsValid(station));
        }
    }

    @Test
    void geoCoordinatesIsValidWithNullValueAndPublicStation() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String nullCoordinates = null;
        station.setGeoCoordinates(nullCoordinates);
        Assertions.assertFalse(Validator.requiredFieldIfPublic(station));
    }

    @Test
    void geoCoordinatesIsValidWithNullValue() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(false);
        String nullCoordinates = null;
        station.setGeoCoordinates(nullCoordinates);
        Assertions.assertTrue(Validator.requiredFieldIfPublic(station));
    }

    @Test
    void geoCoordinatesIsValidWithEmptyValueAndPublicStation() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String emptyCoordinates = "";
        station.setGeoCoordinates(emptyCoordinates);
        Assertions.assertFalse(Validator.requiredFieldIfPublic(station));
    }

    @Test
    void geoCoordinatesIsValidWithEmptyValue() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(false);
        String emptyCoordinates = "";
        station.setGeoCoordinates(emptyCoordinates);
        Assertions.assertTrue(Validator.requiredFieldIfPublic(station));
    }

    @Test
    void connectorTypeIsValid() {
        List<String> validValues = List.of("ccs", "chademo", "type1", "type2", "CCS", "CHADEMO",
                "TYPE1", "TYPE2", "CHAdeMO", "Type1", "Type2");
        for (String value : validValues) {
            Assertions.assertTrue(Validator.connectorTypeIsValid(value));
        }
    }

    @Test
    void connectorTypeIsValidWithInvalidValue() {
        List<String> invalidValues = List.of("Fhxjer", "123456", "Drtgw56");
        for (String value : invalidValues) {
            Assertions.assertFalse(Validator.connectorTypeIsValid(value));
        }
    }

    @Test
    void connectorTypeIsValidWithNullValue() {
        String nullConnectorType = null;
        Assertions.assertFalse(Validator.connectorTypeIsValid(nullConnectorType));
    }

    @Test
    void connectorTypeIsValidWithEmptyValue() {
        String emptyConnectorType = "";
        Assertions.assertFalse(Validator.connectorTypeIsValid(emptyConnectorType));
    }


    @Test
    void connectorMaxPowerKWIsValid() {
        int validPower = 70;
        Assertions.assertTrue(Validator.connectorMaxPowerKWIsValid(validPower));
    }

    @Test
    void connectorMaxPowerKWIsValidWithInvalidValue() {
        int invalidPower1 = -5;
        int invalidPower2 = 5000000;
        Assertions.assertFalse(Validator.connectorMaxPowerKWIsValid(invalidPower1));
        Assertions.assertFalse(Validator.connectorMaxPowerKWIsValid(invalidPower2));
    }
}