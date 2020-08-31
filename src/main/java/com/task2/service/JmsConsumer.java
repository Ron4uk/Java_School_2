package com.task2.service;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.*;
import java.time.LocalDateTime;

@Singleton
public class JmsConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JmsConsumer.class);
    @Inject
    private JmsMessageListener jmsMessageListener;
    private MessageConsumer consumer;

    public void consume() {
        String url = "failover:tcp://localhost:61616";
        ConnectionFactory factory = new ActiveMQConnectionFactory(url);
        try {
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("eCare");

            consumer = session.createConsumer(queue);
            consumer.setMessageListener(jmsMessageListener);
            connection.start();
            LOGGER.info("[{}] JmsConsumer start connection", LocalDateTime.now());
        } catch (JMSException e) {
            LOGGER.error("[{}] [{}] exception: {}", LocalDateTime.now(), LOGGER.getName(), e);

        }
    }
    @PreDestroy
    public void stop(){
        if(consumer!=null){
            try {
                consumer.close();
                LOGGER.info("[{}] JmsConsumer stop connection", LocalDateTime.now());
            } catch (JMSException e) {
                LOGGER.error("ERROR [{}] [{}] JmsConsumer stop connection: {}", LocalDateTime.now(), LOGGER.getName(), e);
            }
        }
    }


}
