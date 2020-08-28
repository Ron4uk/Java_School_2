package com.task2;

import com.task2.dto.TariffDto;
import com.task2.service.JmsConsumer;
import com.task2.service.RestClient;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Named
@Singleton
@Getter
@Setter
@Startup
@ApplicationScoped

public class UiBean implements Serializable {
    @EJB
    private JmsConsumer jmsConsumer;
    @EJB
    private RestClient restClient;



    private Set<TariffDto> tariffDtoSet = new HashSet<>();

    @PostConstruct
    private void init() {
        System.out.println("HELLO! " + restClient);
        jmsConsumer.consume();
        tariffDtoSet = restClient.getTariffs();
    }


}
