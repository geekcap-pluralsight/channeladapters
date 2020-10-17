package com.globomantics.kafkaexample.bazreservationpublisher.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.kafka.inbound.KafkaInboundGateway;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.messaging.MessageChannel;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BazAddressConfig {
    private static final String brokerAddress = "127.0.0.1:9092";

    @Bean public MessageChannel addressChannel() {
        return new DirectChannel();
    }
    @Bean public MessageChannel addressReplyChannel() {
        return new DirectChannel();
    }

    @Bean
    public ProducerFactory<String, String> addressProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, String> addressKafkaTemplate() {
        return new KafkaTemplate<>(addressProducerFactory());
    }

    @Bean
    public KafkaMessageListenerContainer container(ConsumerFactory consumerFactory) {
        ContainerProperties containerProperties = new ContainerProperties("addressTopic");
        containerProperties.setGroupId("addressGroup");
        return new KafkaMessageListenerContainer(consumerFactory, containerProperties);
    }

    @Bean
    public KafkaInboundGateway<String, String, String> inboundGateway(
            AbstractMessageListenerContainer<String, String> container,
            @Qualifier("addressKafkaTemplate") KafkaTemplate<String, String> replyTemplate,
            @Qualifier("addressChannel") MessageChannel channel,
            @Qualifier("addressReplyChannel") MessageChannel replyChannel) {

        replyTemplate.setDefaultTopic("addressReplyTopic");

        KafkaInboundGateway<String, String, String> gateway = new KafkaInboundGateway<>(container, replyTemplate);
        gateway.setRequestChannel(channel);
        gateway.setReplyChannel(replyChannel);
        gateway.setReplyTimeout(30_000);
        return gateway;
    }
}
