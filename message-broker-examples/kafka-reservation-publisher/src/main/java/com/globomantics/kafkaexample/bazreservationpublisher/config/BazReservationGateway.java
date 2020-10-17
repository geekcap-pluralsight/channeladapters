package com.globomantics.kafkaexample.bazreservationpublisher.config;

import com.globomantics.kafkaexample.bazreservationpublisher.model.BazReservation;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "reservationChannel")
public interface BazReservationGateway {
    void publishReservation(BazReservation reservation);
}
