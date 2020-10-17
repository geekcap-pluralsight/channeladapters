package com.globomantics.actimqexample.barreservationpublisher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globomantics.actimqexample.barreservationpublisher.model.BarAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private static final Logger logger = LogManager.getLogger(AddressService.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @ServiceActivator(inputChannel = "getAddressInboundGatewayChannel")
    public String getAddress(Message message) throws JsonProcessingException {
        logger.info("Received message: {}, returning address", message.getPayload());
        return objectMapper.writeValueAsString(new BarAddress(
                "123 Some Street", "Some City", "ST", "12345"));
    }
}
