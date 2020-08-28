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


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;


import javax.ejb.Startup;
import javax.ws.rs.core.MediaType;

import java.util.Set;

@Singleton
public class RestClient {


    public  Set<TariffDto> getTariffs() {
        System.out.println("START getTariffs");
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource("http://localhost:8080/tariff");
        Builder builder = webResource.accept(MediaType.APPLICATION_JSON) //
                .header("content-type", MediaType.APPLICATION_JSON);

        ClientResponse response = builder.get(ClientResponse.class);


        if (response.getStatus() != 200) {
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error = response.getEntity(String.class);
            System.out.println("Error: " + error);
            return null;
        }

        GenericType<Set<TariffDto>> generic = new GenericType<Set<TariffDto>>() {
        };
        Set<TariffDto> tariffDtoList = response.getEntity(generic);

        System.out.println("Output from Server .... \n");

        for (TariffDto tariffDto : tariffDtoList) {
            System.out.println(" --- ");
            System.out.println("tariffDto = " + tariffDto);
            System.out.println("options = " + tariffDto.getOptions());

        }
        return tariffDtoList;
    }
}