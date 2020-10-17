package com.globomantics.kafkaexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.kafka.inbound.KafkaMessageSource;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConsumerProperties;
import org.springframework.messaging.MessageChannel;

@Configuration
public class KafkaInboundConfig {
    @Bean
    public MessageChannel reservationFromKafka() {
        return new DirectChannel();
    }

    @InboundChannelAdapter(channel = "reservationFromKafka", poller = @Poller(fixedDelay = "1000"))
    @Bean
    public KafkaMessageSource<String, String> kafkaSource(ConsumerFactory<String, String> cf) {
        ConsumerProperties consumerProperties = new ConsumerProperties("reservationTopic");
        consumerProperties.setGroupId("reservationGroup");
        return new KafkaMessageSource<>(cf, consumerProperties);
    }
}
