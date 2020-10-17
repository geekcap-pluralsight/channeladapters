package com.globomantics.actimqexample.barreservationpublisher.config;

import com.globomantics.actimqexample.barreservationpublisher.model.BarReservation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class ActiveMQOutboundConfig {

    private static final String DESTINATION_NAME = "reservation-queue";

    @Bean public MessageChannel reservationChannel() {
        return new DirectChannel();
    }
    @Bean public MessageChannel publishReservationToActiveMQ() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "reservationChannel", outputChannel = "publishReservationToActiveMQ")
    public ObjectToJsonTransformer objectToJsonTransformer() {
        return new ObjectToJsonTransformer();
    }

    @Bean
    @ServiceActivator(inputChannel = "publishReservationToActiveMQ")
    public MessageHandler jmsMessageHandler(JmsTemplate jmsTemplate) {
        JmsSendingMessageHandler handler = new JmsSendingMessageHandler(jmsTemplate);
        handler.setDestinationName(DESTINATION_NAME);
        return handler;
    }

    @MessagingGateway(defaultRequestChannel = "reservationChannel")
    public interface BarReservationGateway {
        void publishReservation(BarReservation reservation);
    }
}
