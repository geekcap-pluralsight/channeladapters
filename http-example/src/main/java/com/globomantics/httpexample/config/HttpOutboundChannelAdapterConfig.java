package com.globomantics.httpexample.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.globomantics.httpexample.model.Reservation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.mapping.HeaderMapper;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpOutboundChannelAdapterConfig {

    @Bean
    public MessageChannel toReservationServiceChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "toReservationServiceChannel")
    public MessageHandler postReservationToService() {
        // Create a new HttpRequestExecutingMessageHandler
        HttpRequestExecutingMessageHandler messageHandler = new HttpRequestExecutingMessageHandler(
                "http://localhost:7080/reservation");
        messageHandler.setHttpMethod(HttpMethod.POST);

        // Setup our reply configuration - we do not expect a reply
        messageHandler.setExpectReply(false);

        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "toReservationServiceChannel")
    public interface PublishReservationGateway {
        void publishReservation(Message<Reservation> message);
    }
}
