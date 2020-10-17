package com.globomantics.jdbcexample.service;

import com.globomantics.jdbcexample.model.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class ReservationListener {
    private static final Logger logger = LogManager.getLogger(ReservationListener.class);

    @ServiceActivator(inputChannel = "newReservationChannel")
    public void handleReservation(Reservation reservation) {
        logger.info("Received message: {}", reservation);
    }
}
