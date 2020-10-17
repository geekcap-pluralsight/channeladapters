package com.globomantics.actimqexample.barreservationpublisher.service;

import com.globomantics.actimqexample.barreservationpublisher.config.ActiveMQOutboundConfig;
import com.globomantics.actimqexample.barreservationpublisher.model.BarReservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarReservationService {
    private static final Logger logger = LogManager.getLogger(BarReservationService.class);

    @Autowired
    private ActiveMQOutboundConfig.BarReservationGateway gateway;

    public void publishReservation(BarReservation reservation) {
        logger.info("Publishing reservation {} for user {}", reservation.getId(), reservation.getName());
        gateway.publishReservation(reservation);
    }
}
