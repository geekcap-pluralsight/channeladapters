package com.globomantics.kafkaexample.bazreservationpublisher.service;

import com.globomantics.kafkaexample.bazreservationpublisher.config.BazReservationGateway;
import com.globomantics.kafkaexample.bazreservationpublisher.model.BazReservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BazReservationService {
    private static final Logger logger = LogManager.getLogger(BazReservationService.class);

    @Autowired
    private BazReservationGateway gateway;

    public void publishReservation(BazReservation reservation) {
        logger.info("Publishing reservation {} for user {}", reservation.getId(), reservation.getName());
        gateway.publishReservation(reservation);
    }
}
