package org.example.chargingstationvalidationservice.services.impl;

import org.example.chargingstationvalidationservice.models.Response;
import org.example.chargingstationvalidationservice.repositories.ResponseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ResponseServiceImplTest {
    @Mock
    private ResponseRepository responseRepository;

    @InjectMocks
    private ResponseServiceImpl responseService;

    @Test
    public void testAddResponse() {
        Response response = new Response();
        response.setId(UUID.randomUUID());
        response.setIdStation("TestStationId");
        response.setMessage(new StringBuilder("Test message"));

        when(responseRepository.save(response)).thenReturn(response);

        Response savedResponse = responseService.addResponse(response);

        verify(responseRepository, times(1)).save(response);

        assertNotNull(savedResponse);

        assertEquals(response.getId(), savedResponse.getId());
        assertEquals(response.getIdStation(), savedResponse.getIdStation());
        assertEquals(response.getMessage(), savedResponse.getMessage());
    }
}