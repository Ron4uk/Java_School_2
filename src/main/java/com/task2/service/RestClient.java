package com.task2.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import com.sun.jersey.api.json.JSONConfiguration;
import com.task2.dto.TariffDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;


import javax.ejb.Startup;
import javax.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.util.Set;

@Singleton
public class RestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);


    public Set<TariffDto> getTariffs() {
        LOGGER.info("[{}] [{}] getTariffs", LocalDateTime.now(), LOGGER.getName());
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource("http://localhost:8080/tariff");
        Builder builder = webResource.accept(MediaType.APPLICATION_JSON)
                .header("content-type", MediaType.APPLICATION_JSON);
        ClientResponse response = builder.get(ClientResponse.class);

        if (response.getStatus() != 200) {
            String error = response.getEntity(String.class);
            LOGGER.error("[{}] [{}] error response status : {}", LocalDateTime.now(), LOGGER.getName(), response.getStatus());
            LOGGER.error("[{}] [{}] error getTariffs : {}", LocalDateTime.now(), LOGGER.getName(), error);
            return null;

        }

        GenericType<Set<TariffDto>> generic = new GenericType<Set<TariffDto>>() {
        };
        Set<TariffDto> tariffDtoList = response.getEntity(generic);
        LOGGER.info("[{}] [{}] getTariffs from rest request: {}", LocalDateTime.now(), LOGGER.getName(), tariffDtoList);

        return tariffDtoList;
    }
}