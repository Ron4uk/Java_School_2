package com.task2.service;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.*;

@Singleton
public class JmsConsumer {
    @Inject
    private JmsMessageListener jmsMessageListener;

    public void consume() {
        System.out.println("start CONSUME");
        String url = "tcp://localhost:61616";
        ConnectionFactory factory = new ActiveMQConnectionFactory(url);
        try {
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("eCare");

            MessageConsumer consumer = session.createConsumer(queue);
            //JmsMessageListener listener = new JmsMessageListener();
            consumer.setMessageListener(jmsMessageListener);

            connection.start();
        } catch (JMSException exp) {
            exp.printStackTrace();
        }
    }
}
