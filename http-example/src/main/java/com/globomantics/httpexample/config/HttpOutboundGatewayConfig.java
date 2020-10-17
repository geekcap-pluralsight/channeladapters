package com.globomantics.httpexample.config;

import java.util.Collections;

import com.globomantics.httpexample.model.Reservation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class HttpOutboundGatewayConfig {
    @Bean public MessageChannel getReservationChannel() {
        return new DirectChannel();
    }
    @Bean public MessageChannel getReservationReplyChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "getReservationChannel")
    public MessageHandler httpOutboundGateway() {
        HttpRequestExecutingMessageHandler messageHandler = new HttpRequestExecutingMessageHandler(
                "http://localhost:7080/reservation/{reservationId}");
        messageHandler.setHttpMethod(HttpMethod.GET);
        messageHandler.setExpectReply(true);
        messageHandler.setExpectedResponseType(Reservation.class);
        SpelExpressionParser parser = new SpelExpressionParser();
        messageHandler.setUriVariableExpressions(Collections.singletonMap(
                "reservationId", parser.parseRaw("payload")));
        messageHandler.setOutputChannelName("getReservationReplyChannel");
        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "getReservationChannel", defaultReplyChannel = "getReservationReplyChannel")
    public interface GetReservationGateway {
        Reservation getReservation(Long id);
    }
}
