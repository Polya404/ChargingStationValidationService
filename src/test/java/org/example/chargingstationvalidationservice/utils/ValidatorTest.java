package org.example.chargingstationvalidationservice.utils;

import org.example.chargingstationvalidationservice.models.ChargingStation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;


class ValidatorTest {

    @Test
    void idIsValid() {
        String invalidId = "123";
        String emptyId = "";
        String nullId = null;
        String validId = UUID.randomUUID().toString();
        Assertions.assertFalse(Validator.idIsValid(invalidId));
        Assertions.assertFalse(Validator.idIsValid(emptyId));
        Assertions.assertFalse(Validator.idIsValid(nullId));
        Assertions.assertTrue(Validator.idIsValid(validId));
    }

    @Test
    void tittleIsValid() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String emptyTittle = "";
        String nullTittle = null;
        String validTittle = "Welcome";
        station.setTitle(emptyTittle);
        Assertions.assertFalse(Validator.tittleIsValid(station));
        station.setTitle(nullTittle);
        Assertions.assertFalse(Validator.tittleIsValid(station));
        station.setTitle(validTittle);
        Assertions.assertTrue(Validator.tittleIsValid(station));
    }

    @Test
    void descriptionIsValid() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String emptyDescription = "";
        String nullDescription = null;
        String validDescription = "Something";
        station.setDescription(emptyDescription);
        Assertions.assertFalse(Validator.descriptionIsValid(station));
        station.setDescription(nullDescription);
        Assertions.assertFalse(Validator.descriptionIsValid(station));
        station.setDescription(validDescription);
        Assertions.assertTrue(Validator.descriptionIsValid(station));
    }

    @Test
    void addressIsValid() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String emptyAddress = "";
        String nullAddress = null;
        String invalidAddress = "Something";
        String validAddress = "test@mail.com";
        station.setAddress(emptyAddress);
        Assertions.assertFalse(Validator.requiredFieldIfPublic(station));
        station.setAddress(nullAddress);
        Assertions.assertFalse(Validator.requiredFieldIfPublic(station));
        station.setAddress(invalidAddress);
        Assertions.assertFalse(Validator.addressIsValid(station));
        station.setAddress(validAddress);
        Assertions.assertTrue(Validator.addressIsValid(station));
    }

    @Test
    void geoCoordinatesIsValid() {
        ChargingStation station = new ChargingStation();
        station.setIsPublic(true);
        String emptyCoordinates = "";
        String nullCoordinates = null;
        String validCoordinates1 = "+90.0, -127.554334";
        String validCoordinates2 = "45, 180";
        String validCoordinates3 = "-90, -180";
        String validCoordinates4 = "-90.000, -180.0000";
        String validCoordinates5 = "+90, +180";
        String validCoordinates6 = "47.1231231, 179.99999999";
        String validCoordinates7 = "50.4477, 30.5233";
        String invalidCoordinates1 = "-90., -180.";
        String invalidCoordinates2 = "+90.1, -100.111";
        String invalidCoordinates3 = "-91, 123.456";
        String invalidCoordinates4 = "045, 180";
        station.setGeoCoordinates(validCoordinates1);
        Assertions.assertTrue(Validator.geoCoordinatesIsValid(station));
        station.setGeoCoordinates(validCoordinates2);
        Assertions.assertTrue(Validator.geoCoordinatesIsValid(station));
        station.setGeoCoordinates(validCoordinates3);
        Assertions.assertTrue(Validator.geoCoordinatesIsValid(station));
        station.setGeoCoordinates(validCoordinates4);
        Assertions.assertTrue(Validator.geoCoordinatesIsValid(station));
        station.setGeoCoordinates(validCoordinates5);
        Assertions.assertTrue(Validator.geoCoordinatesIsValid(station));
        station.setGeoCoordinates(validCoordinates6);
        Assertions.assertTrue(Validator.geoCoordinatesIsValid(station));
        station.setGeoCoordinates(validCoordinates7);
        Assertions.assertTrue(Validator.geoCoordinatesIsValid(station));
        station.setGeoCoordinates(invalidCoordinates1);
        Assertions.assertFalse(Validator.geoCoordinatesIsValid(station));
        station.setGeoCoordinates(invalidCoordinates2);
        Assertions.assertFalse(Validator.geoCoordinatesIsValid(station));
        station.setGeoCoordinates(invalidCoordinates3);
        Assertions.assertFalse(Validator.geoCoordinatesIsValid(station));
        station.setGeoCoordinates(invalidCoordinates4);
        Assertions.assertFalse(Validator.geoCoordinatesIsValid(station));
        station.setGeoCoordinates(emptyCoordinates);
        Assertions.assertFalse(Validator.requiredFieldIfPublic(station));
        station.setGeoCoordinates(nullCoordinates);
        Assertions.assertFalse(Validator.requiredFieldIfPublic(station));
    }

    @Test
    void connectorTypeIsValid() {
        String invalidConnectorType1 = "Fhxjer";
        String invalidConnectorType2 = "123456";
        String invalidConnectorType3 = "Drtgw56";
        String emptyConnectorType = "";
        String nullConnectorType = null;
        String validConnectorType1 = "ccs";
        String validConnectorType2 = "chademo";
        String validConnectorType3 = "type1";
        String validConnectorType4 = "type2";
        String validConnectorType5 = "CCS";
        String validConnectorType6 = "CHADEMO";
        String validConnectorType7 = "TYPE1";
        String validConnectorType8 = "TYPE2";
        String validConnectorType9 = "CCS";
        String validConnectorType10 = "CHAdeMO";
        String validConnectorType11 = "Type1";
        String validConnectorType12 = "Type2";
        Assertions.assertFalse(Validator.connectorTypeIsValid(invalidConnectorType1));
        Assertions.assertFalse(Validator.connectorTypeIsValid(invalidConnectorType2));
        Assertions.assertFalse(Validator.connectorTypeIsValid(invalidConnectorType3));
        Assertions.assertFalse(Validator.connectorTypeIsValid(emptyConnectorType));
        Assertions.assertFalse(Validator.connectorTypeIsValid(nullConnectorType));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType1));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType2));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType3));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType4));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType5));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType6));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType7));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType8));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType9));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType10));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType11));
        Assertions.assertTrue(Validator.connectorTypeIsValid(validConnectorType12));
    }

    @Test
    void connectorMaxPowerKWIsValid(){
        int invalidPower1 = -5;
        int invalidPower2 = 5000000;
        int validPower = 70;
        Assertions.assertFalse(Validator.connectorMaxPowerKWIsValid(invalidPower1));
        Assertions.assertFalse(Validator.connectorMaxPowerKWIsValid(invalidPower2));
        Assertions.assertTrue(Validator.connectorMaxPowerKWIsValid(validPower));
    }
}