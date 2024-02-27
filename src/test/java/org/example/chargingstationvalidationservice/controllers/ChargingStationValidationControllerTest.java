package org.example.chargingstationvalidationservice.controllers;

import org.example.chargingstationvalidationservice.models.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;

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
                "  \"connectors\": [\n" +
                "    {\n" +
                "      \"id\": \"86b9b1bb-8614-4915-b496-517bbc351788\",\n" +
                "      \"type\": \"CHAdeMO\",\n" +
                "      \"maxPowerKw\": 30\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        HttpEntity<String> entity = getHttpEntity(jsonRequest, "ua");

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", entity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "Правильно";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }



    @Test
    public void validDataWhenStationIsPublic() {
        String jsonRequest = "{\n" +
                "    \"id\": \"86b9b1bb-8614-4915-b496-517bbc351739\",\n" +
                "    \"title\": \"Station Title\",\n" +
                "    \"description\": \"Station Description\",\n" +
                "    \"address\": \"test@mail.com\",\n" +
                "    \"geoCoordinates\": \"46.430095, 30.696315\",\n" +
                "    \"isPublic\": true,\n" +
                "    \"connectors\": [\n" +
                "        {\n" +
                "        \"id\": \"86b9b1bb-8664-4915-b496-517bbc351739\",\n" +
                "        \"type\": \"CCS\",\n" +
                "        \"maxPowerKw\": 50\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"86b9b1bb-8614-4915-b496-517bbc351788\",\n" +
                "        \"type\": \"CHAdeMO\",\n" +
                "        \"maxPowerKw\": 30\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        HttpEntity<String> entity = getHttpEntity(jsonRequest, "en");
        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", entity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "Correct";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void validDataWhenStationIsntPublic() {
        String jsonRequest = "{\n" +
                "  \"id\": \"86b9b1bb-8614-4915-b496-517bbc351739\",\n" +
                "  \"isPublic\": false,\n" +
                "  \"connectors\": [\n" +
                "    {\n" +
                "      \"id\": \"86b9b1bb-8614-4915-b496-517bbc351788\",\n" +
                "      \"type\": \"CHAdeMO\",\n" +
                "      \"maxPowerKw\": 30\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        HttpEntity<String> entity = getHttpEntity(jsonRequest, "en");

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", entity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "Correct";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void withInvalidAddress() {
        String jsonRequest = "{\n" +
                "  \"id\": \"86b9b1bb-8614-4915-b496-517bbc351739\",\n" +
                "  \"isPublic\": false,\n" +
                "    \"address\": \"testmail.com\",\n" +
                "  \"connectors\": [\n" +
                "    {\n" +
                "      \"id\": \"86b9b1bb-8614-4915-b496-517bbc351788\",\n" +
                "      \"type\": \"CHAdeMO\",\n" +
                "      \"maxPowerKw\": 30\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        HttpEntity<String> entity = getHttpEntity(jsonRequest, "en");

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", entity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "The email address does not match the mailing address template. ";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void withInvalidCoordinates() {
        String jsonRequest = "{\n" +
                "  \"id\": \"86b9b1bb-8614-4915-b496-517bbc351739\",\n" +
                "  \"isPublic\": false,\n" +
                "    \"geoCoordinates\": \"46.430095N, 30.696315W\",\n" +
                "  \"connectors\": [\n" +
                "    {\n" +
                "      \"id\": \"86b9b1bb-8614-4915-b496-517bbc351788\",\n" +
                "      \"type\": \"CHAdeMO\",\n" +
                "      \"maxPowerKw\": 30\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        HttpEntity<String> entity = getHttpEntity(jsonRequest, "en");

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", entity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "Incorrect coordinates. Try entering coordinates without letters. For example 46.430095, 30.696315. ";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    @Test
    public void withInvalidId() {
        String jsonRequest = "{\n" +
                "  \"id\": \"86b9b1bb-8614-4915-b496-517bbc339\",\n" +
                "  \"isPublic\": false,\n" +
                "  \"connectors\": [\n" +
                "    {\n" +
                "      \"id\": \"86b9b1bb-8614-4915-b496-517bbc351788\",\n" +
                "      \"type\": \"CHAdeMO\",\n" +
                "      \"maxPowerKw\": 30\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        HttpEntity<String> entity = getHttpEntity(jsonRequest, "en");

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", entity, Response.class);
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
                "  \"address\": \"test@mail.com\",\n" +
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
                "  \"address\": \"test@mail.com\",\n" +
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
        String jsonRequest = "{\n" +
                "    \"id\": \"86b9b1bb-8614-4915-b496-517bbc351739\",\n" +
                "    \"description\": \"Station Description\",\n" +
                "    \"address\": \"test@mail.com\",\n" +
                "    \"geoCoordinates\": \"46.430095, 30.696315\",\n" +
                "    \"isPublic\": true,\n" +
                "    \"connectors\": [\n" +
                "        {\n" +
                "        \"id\": \"86b9b1bb-8664-4915-b496-517bbc351739\",\n" +
                "        \"type\": \"CCS\",\n" +
                "        \"maxPowerKw\": 50\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"86b9b1bb-8614-4915-b496-517bbc351788\",\n" +
                "        \"type\": \"CHAdeMO\",\n" +
                "        \"maxPowerKw\": 30\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        HttpEntity<String> entity = getHttpEntity(jsonRequest, "en");

        ResponseEntity<Response> responseEntity = restTemplate.postForEntity("/validate", entity, Response.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String message = Objects.requireNonNull(responseEntity.getBody()).getMessage();
        String expectedMessage = "Title cannot be empty. ";
        assertFalse(message.isEmpty());
        assertEquals(expectedMessage, message);
    }

    private HttpEntity<String> getHttpEntity(String jsonRequest, String language) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept-Language", language);
        return new HttpEntity<>(jsonRequest, headers);
    }

}