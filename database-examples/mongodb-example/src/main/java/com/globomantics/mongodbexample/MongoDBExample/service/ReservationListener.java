package com.globomantics.mongodbexample.MongoDBExample.service;

import com.globomantics.mongodbexample.MongoDBExample.config.OutboundChannelAdapterConfig;
import com.globomantics.mongodbexample.MongoDBExample.config.OutboundGatewayConfig;
import com.globomantics.mongodbexample.MongoDBExample.model.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class ReservationListener {
    private static final Logger logger = LogManager.getLogger(ReservationListener.class);

    @Autowired
    private OutboundChannelAdapterConfig.UpdateReservationGateway updateReservationGateway;

    @Autowired
    private OutboundGatewayConfig.QueryReservationGateway queryReservationGateway;

    @ServiceActivator(inputChannel = "reservationFromMongoChannel")
    public void handleReservation(Reservation reservation) {
        logger.info("Received Reservation: {}", reservation);

        reservation.setStatus("Processed");
        updateReservationGateway.updateReservation(reservation);

        Reservation updatedReservation = queryReservationGateway.query("{id: '" + reservation.getId() + "'}");
        logger.info("Updated Reservation: {}", updatedReservation);
    }
}
