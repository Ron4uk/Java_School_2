package com.task2.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.task2.UiBean;
import com.task2.dto.TariffDto;
import lombok.Getter;
import lombok.Setter;


import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.websocket.Session;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;


@Singleton
@LocalBean
public class JmsMessageListener implements MessageListener, Serializable {
    private Set<TariffDto> tariffDtoSet = new HashSet<>();
    @Inject
    private UiBean uiBean;
    @Inject
    @Push
    private PushContext push;


    @Override
    public void onMessage(final Message msg) {
        System.out.println("--------");
        System.out.println("onMessage");
        System.out.println(uiBean);
        try {
            TextMessage textMessage = (TextMessage) msg;
            Type itemsListType = new TypeToken<Set<TariffDto>>() {
            }.getType();
            tariffDtoSet = new Gson().fromJson(textMessage.getText(), itemsListType);
            uiBean.setTariffDtoSet(tariffDtoSet);

            push.send("hello");
            push.send(tariffDtoSet);
            System.out.println(uiBean.getTariffDtoSet());
            for (TariffDto tariffDto : tariffDtoSet) {
                System.out.println(" --- ");
                System.out.println("tariffDto = " + tariffDto);
                System.out.println("options = " + tariffDto.getOptions());

            }

        } catch (JMSException e) {
            e.printStackTrace();
        }


    }
}
