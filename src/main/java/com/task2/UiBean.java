package com.task2;

import com.task2.dto.TariffDto;
import com.task2.service.JmsConsumer;
import com.task2.service.RestClient;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Named
@Singleton
@Getter
@Setter
@Startup
@ApplicationScoped
public class UiBean implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(UiBean.class);
    @EJB
    private JmsConsumer jmsConsumer;
    @EJB
    private RestClient restClient;
    @Inject
    @Push(channel = "listener")
    private PushContext push;
    private double minPrice;
    private int amountOfOptions;

    private Set<TariffDto> tariffDtoSet = new HashSet<>();

    @PostConstruct
    private void init() {
        jmsConsumer.consume();
        tariffDtoSet = restClient.getTariffs();
        findMinPriceAndMaxSize();

    }


    public void updateTariffs() {
        LOGGER.info("[{}] [{}] updateTariffs.  Pushcontext: {}", LocalDateTime.now(), LOGGER.getName(), push.toString());
        findMinPriceAndMaxSize();
        push.send(true);

    }

    public void findMinPriceAndMaxSize() {
        minPrice = tariffDtoSet.stream().min(TariffDto::comparePrice).get().getPrice().doubleValue();
        amountOfOptions = tariffDtoSet.stream().max(TariffDto::compareOptions).get().getOptions().size();
        System.out.println("minPrice " + minPrice);
        System.out.println("amountOfOptions " + amountOfOptions);
    }

    public void startListener() {
        LOGGER.info("Websocket is started [{}]", LocalDateTime.now());
    }

    public void closeListener() {
        LOGGER.info("Websocket is closed [{}]", LocalDateTime.now());
    }


}
