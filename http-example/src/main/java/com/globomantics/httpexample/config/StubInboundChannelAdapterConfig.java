package com.globomantics.httpexample.config;

import java.util.ArrayList;
import java.util.List;

import com.globomantics.httpexample.model.Reservation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@Configuration
public class StubInboundChannelAdapterConfig {
    @Bean
    public MessageChannel reservationFromStubChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel reservationListFromStubChannel() {
        return new DirectChannel();
    }

    @Splitter(inputChannel = "reservationListFromStubChannel", outputChannel = "reservationFromStubChannel")
    public List<Reservation> splitter(Message<List<Reservation>> message) {
        return message.getPayload();
    }

    @Bean
    @InboundChannelAdapter(value = "reservationListFromStubChannel", poller = @Poller(fixedDelay = "5000"))
    public MessageSource<List<Reservation>> stubMessageSource() {
        return () -> {
            List<Reservation> reservations = new ArrayList<>();
            reservations.add(new Reservation(1L, "Smith", "None", 1));
            reservations.add(new Reservation(2L, "Jones", "None", 1));
            return MessageBuilder.withPayload(reservations).build();
        };
    }
}
