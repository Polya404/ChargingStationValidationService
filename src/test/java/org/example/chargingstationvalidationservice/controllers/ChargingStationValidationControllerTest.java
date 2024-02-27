package org.example.chargingstationvalidationservice.controllers;

import org.example.chargingstationvalidationservice.models.ChargingStation;
import org.example.chargingstationvalidationservice.models.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@ComponentScan(basePackages = "org.example.chargingstationvalidationservice")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChargingStationValidationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testValidateChargingStation_ValidInput() {
        String jsonRequest = "{\n" +
                "  \"id\": \"86b9b1bb-8614-4915-b496-517bbc351739\",\n" +
                "  \"isPublic\": false,\n" +
                "  \"connectors\": []\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept-Language", "ua");
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", entity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "Правильно";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void validDataWhenStationIsPublic() {
        ChargingStation station = new ChargingStation();
        station.setTitle("Test Station");
        station.setDescription("Test Description");
        station.setId(UUID.randomUUID().toString());
        station.setIsPublic(true);
        station.setAddress("123 Main St, City, Country");
        station.setGeoCoordinates("46.430095, 30.696315");
        station.setConnectors(Collections.emptyList());

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", station, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "Correct";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void validDataWhenStationIsntPublic() {
        ChargingStation station = new ChargingStation();
        station.setTitle("Test Station");
        station.setDescription("Test Description");
        station.setId(UUID.randomUUID().toString());
        station.setIsPublic(false);
        station.setConnectors(Collections.emptyList());

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", station, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "Correct";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void withInvalidAddress() {
        ChargingStation station = new ChargingStation();
        station.setTitle("Test Station");
        station.setDescription("Test Description");
        station.setId(UUID.randomUUID().toString());
        station.setIsPublic(true);
        station.setAddress("City, Country");
        station.setGeoCoordinates("46.430095, 30.696315");
        station.setConnectors(Collections.emptyList());

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", station, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "Incorrect address. The address must contain the country, city, street and number. ";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void withInvalidCoordinates() {
        ChargingStation station = new ChargingStation();
        station.setTitle("Test Station");
        station.setDescription("Test Description");
        station.setId(UUID.randomUUID().toString());
        station.setIsPublic(true);
        station.setAddress("123 Main St, City, Country");
        station.setGeoCoordinates("46.430095 N, 30.696315 W");
        station.setConnectors(Collections.emptyList());

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", station, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "Incorrect coordinates. Try entering coordinates without letters. For example 46.430095, 30.696315. ";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void withInvalidId() {
        ChargingStation station = new ChargingStation();
        station.setTitle("Test Station");
        station.setDescription("Test Description");
        station.setId("smth");
        station.setIsPublic(true);
        station.setAddress("123 Main St, City, Country");
        station.setGeoCoordinates("46.430095, 30.696315");
        station.setConnectors(Collections.emptyList());

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", station, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "You have entered an invalid ID. The ID must correspond to the form \"86b9b1bb-8614-4915-b496-517bbc351739\". ";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void withInvalidConnectorType() {
        String jsonRequest = "{\n" +
                "  \"id\": \"86b9b1bb-8614-4915-b496-517bbc351739\",\n" +
                "  \"title\": \"Station Title\",\n" +
                "  \"description\": \"Station Description\",\n" +
                "  \"address\": \"123 Main St, City, Country\",\n" +
                "  \"geoCoordinates\": \"46.430095, 30.696315\",\n" +
                "  \"isPublic\": true,\n" +
                "  \"connectors\": [\n" +
                "    {\n" +
                "      \"id\": \"86b9b1bb-8614-4915-b496-517bbc351788\",\n" +
                "      \"type\": \"CHAdeMOx\",\n" +
                "      \"maxPowerKw\": 30\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", entity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "For chargingStation with id 86b9b1bb-8614-4915-b496-517bbc351739, connector type - CHAdeMOx, for connector with id 86b9b1bb-8614-4915-b496-517bbc351788 incorrect. ";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void withInvalidConnectorMaxPower() {
        String jsonRequest = "{\n" +
                "  \"id\": \"86b9b1bb-8614-4915-b496-517bbc351739\",\n" +
                "  \"title\": \"Station Title\",\n" +
                "  \"description\": \"Station Description\",\n" +
                "  \"address\": \"123 Main St, City, Country\",\n" +
                "  \"geoCoordinates\": \"46.430095, 30.696315\",\n" +
                "  \"isPublic\": true,\n" +
                "  \"connectors\": [\n" +
                "    {\n" +
                "      \"id\": \"86b9b1bb-8614-4915-b496-517bbc351788\",\n" +
                "      \"type\": \"CHAdeMO\",\n" +
                "      \"maxPowerKw\": 0\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", entity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "For chargingStation with id 86b9b1bb-8614-4915-b496-517bbc351739, MaxPower - 0 for connector with id 86b9b1bb-8614-4915-b496-517bbc351788 incorrect. ";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void withoutTittleWhenStationIsPublic() {
        ChargingStation station = new ChargingStation();
        station.setDescription("Test Description");
        station.setId(UUID.randomUUID().toString());
        station.setIsPublic(true);
        station.setAddress("123 Main St, City, Country");
        station.setGeoCoordinates("46.430095, 30.696315");
        station.setConnectors(Collections.emptyList());

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", station, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "Title cannot be empty. ";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

}