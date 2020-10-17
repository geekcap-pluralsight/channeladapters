package com.globomantics.actimqexample.barreservationpublisher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globomantics.actimqexample.barreservationpublisher.config.ActiveMQOutboundGateway;
import com.globomantics.actimqexample.barreservationpublisher.model.BarAddress;
import com.globomantics.actimqexample.barreservationpublisher.model.BarReservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class BarReservationListener {
    private static final Logger logger = LogManager.getLogger(BarReservationListener.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ActiveMQOutboundGateway.AddressGateway addressGateway;

    @ServiceActivator(inputChannel = "reservationConsumerChannel")
    public void handleMessage(String message) throws JsonProcessingException {
        BarReservation reservation = objectMapper.readValue(message, BarReservation.class);
        logger.info("Received Message: {}", reservation);

        String addressString = addressGateway.getAddress(reservation.getId());
        BarAddress address = objectMapper.readValue(addressString, BarAddress.class);
        logger.info("Received address: {}", address);
    }
}
