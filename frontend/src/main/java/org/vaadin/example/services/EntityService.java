package org.vaadin.example.services;

import org.vaadin.example.model.DataModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service
public class EntityService {
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api";

    public EntityService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<DataModel> findAll() {
        DataModel[] data = restTemplate.getForObject(baseUrl, DataModel[].class);
        return Arrays.asList(data);
    }

    public void delete(DataModel entity) {
        restTemplate.delete(baseUrl + "/" + entity.getId());
    }

}
