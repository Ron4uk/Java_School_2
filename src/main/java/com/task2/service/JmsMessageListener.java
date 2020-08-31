package com.task2.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.task2.UiBean;
import com.task2.dto.TariffDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.inject.Inject;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Singleton
@LocalBean
public class JmsMessageListener implements MessageListener, Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageListener.class);
    private Set<TariffDto> tariffDtoSet = new HashSet<>();
    @Inject
    private UiBean uiBean;


    @Override
    public void onMessage(final Message msg) {
        LOGGER.info("[{}] [{}] onMessage start", LocalDateTime.now(), LOGGER.getName());
        try {
            TextMessage textMessage = (TextMessage) msg;
            Type itemsListType = new TypeToken<Set<TariffDto>>() {
            }.getType();
            tariffDtoSet = new Gson().fromJson(textMessage.getText(), itemsListType);
            uiBean.setTariffDtoSet(tariffDtoSet);
            uiBean.updateTariffs();
            LOGGER.info("[{}] [{}] onMessage: {}", LocalDateTime.now(), LOGGER.getName(), tariffDtoSet);

        } catch (JMSException e) {
            LOGGER.error("[{}] [{}] exception: {}", LocalDateTime.now(), LOGGER.getName(), e);
        }


    }
}
