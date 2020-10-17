package com.globomantics.rabbitmqexample.service;

import com.globomantics.rabbitmqexample.config.RabbitMQOutboundGatewayConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FooReservationListener {
    private static final Logger logger = LogManager.getLogger(FooReservationListener.class);

    @Autowired
    private RabbitMQOutboundGatewayConfig.AddressGateway addressGateway;

    @ServiceActivator(inputChannel = "fooReservationListenerChannel")
    public void handleMessage(Message message) {
        logger.info("Message: {}", message.getPayload());

        Message address = addressGateway.getAddress(1L);
        logger.info("Address: {}", address.getPayload());
    }
}
