package com.globomantics.kafkaexample.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaOutboundGatewayConfig {
    private static final String brokerAddress = "127.0.0.1:9092";
    @Bean public MessageChannel bazAddressChannel() {
        return new DirectChannel();
    }

    @Bean
    public KafkaMessageListenerContainer container(ConsumerFactory consumerFactory) {
        ContainerProperties containerProperties = new ContainerProperties("addressReplyTopic");
        containerProperties.setGroupId("addressGroup");
        return new KafkaMessageListenerContainer(consumerFactory, containerProperties);
    }
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(props);
    }
    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(
            ProducerFactory<String, String> producerFactory,
            KafkaMessageListenerContainer container) {
        return new ReplyingKafkaTemplate<String, String, String>(producerFactory, container);
    }
    @Bean
    @ServiceActivator(inputChannel = "bazAddressChannel")
    public KafkaProducerMessageHandler<String, String> outGateway(
            ReplyingKafkaTemplate<String, String, String> kafkaTemplate) {
        KafkaProducerMessageHandler<String, String> handler = new KafkaProducerMessageHandler<>(kafkaTemplate);
        handler.setTopicExpression(new LiteralExpression("addressTopic"));
        handler.setMessageKeyExpression(new LiteralExpression("addressKey"));
        return handler;
    }

    @MessagingGateway(defaultRequestChannel = "bazAddressChannel")
    public interface AddressGateway {
        Message getAddress(String userId);
    }
}
