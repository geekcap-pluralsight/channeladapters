package com.globomantics.httpexample.service;

import com.globomantics.httpexample.config.HttpOutboundChannelAdapterConfig;
import com.globomantics.httpexample.config.HttpOutboundGatewayConfig;
import com.globomantics.httpexample.model.Reservation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ReservationListener {
    private static Logger logger = LogManager.getLogger(ReservationListener.class);

    @Autowired
    private HttpOutboundChannelAdapterConfig.PublishReservationGateway publishReservationGateway;

    @Autowired
    private HttpOutboundGatewayConfig.GetReservationGateway getReservationGateway;

    @ServiceActivator(inputChannel = "reservationFromStubChannel")
    public void handleReservation(Message<Reservation> message) {
        Reservation reservation = message.getPayload();

        logger.info("Received reservation from channel: {}, publishing it to the reservation web service", reservation);
        publishReservationGateway.publishReservation(MessageBuilder.withPayload(reservation)
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build());

        Reservation serviceReservation = getReservationGateway.getReservation(reservation.getId());
        logger.info("Reservation from web service: {}", serviceReservation);

    }
}
