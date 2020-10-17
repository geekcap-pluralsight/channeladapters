package com.globomantics.customadapters.config;

import java.util.List;

import com.globomantics.customadapters.model.Reservation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@Configuration
public class CustomInboundChannelAdapterConfig {
    @Bean
    public MessageChannel reservationFromCustomChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel reservationListFromCustomChannel() {
        return new DirectChannel();
    }

    @Splitter(inputChannel = "reservationListFromCustomChannel", outputChannel = "reservationFromCustomChannel")
    public List<Reservation> splitter(Message<List<Reservation>> message) {
        return message.getPayload();
    }

    @Bean
    @InboundChannelAdapter(value = "reservationListFromCustomChannel", poller = @Poller(fixedDelay = "5000"))
    public MessageSource directoryMonitorMessageSource() {
        return new DirectoryMonitorMessageSource("reservations", Reservation.class);
    }
}
