package com.globomantics.rabbitmqexample.config;

import com.globomantics.rabbitmqexample.model.FooReservation;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "reservationChannel")
public interface FooReservationGateway {
    void publishReservation(FooReservation fooReservation);
}
