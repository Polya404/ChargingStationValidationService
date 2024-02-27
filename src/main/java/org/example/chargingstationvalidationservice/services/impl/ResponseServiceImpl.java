package org.example.chargingstationvalidationservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.chargingstationvalidationservice.models.Response;
import org.example.chargingstationvalidationservice.repositories.ResponseRepository;
import org.example.chargingstationvalidationservice.services.ResponseService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final ResponseRepository responseRepository;
    @Override
    public Response addResponse(Response response) {
        return responseRepository.save(response);
    }
}
