package com.globomantics.rabbitmqexample.service;

import com.globomantics.rabbitmqexample.model.FooAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private static final Logger logger = LogManager.getLogger(AddressService.class);

    @ServiceActivator(inputChannel = "getAddressInputChannel")
    public String getAddress(Long id) {
        logger.info("Received a request for the address for reservation {}", id);
        return "this is my address";
    }
}
