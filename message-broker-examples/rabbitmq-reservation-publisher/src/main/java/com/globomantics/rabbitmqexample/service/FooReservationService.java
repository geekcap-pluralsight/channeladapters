package com.globomantics.rabbitmqexample.service;

import com.globomantics.rabbitmqexample.config.FooReservationGateway;
import com.globomantics.rabbitmqexample.config.RabbitMQConfig;
import com.globomantics.rabbitmqexample.model.FooReservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FooReservationService {
    private static final Logger logger = LogManager.getLogger(FooReservationService.class);

    @Autowired
    private FooReservationGateway gateway;

    public void publishReservation(FooReservation reservation) {
        logger.info("Publishing reservation {} for user {}", reservation.getId(), reservation.getName());
        gateway.publishReservation(reservation);
    }
}
