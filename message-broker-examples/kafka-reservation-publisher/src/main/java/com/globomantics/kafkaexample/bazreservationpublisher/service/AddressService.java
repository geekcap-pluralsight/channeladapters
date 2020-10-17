package com.globomantics.kafkaexample.bazreservationpublisher.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private static final Logger logger = LogManager.getLogger(AddressService.class);

    @ServiceActivator(inputChannel = "addressChannel")
    public String getAddress(String id) {
        logger.info("Address request for user {}", id);
        return "this is my address";
    }
}