package com.globomantics.rabbitmqexample.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@Configuration
public class RabbitMQOutboundGatewayConfig {
    static final String ADDRESS_QUEUE = "get-address";

    @Bean
    public MessageChannel fooAddressChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "fooAddressChannel")
    public AmqpOutboundEndpoint amqpOutbound(AmqpTemplate amqpTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
        outbound.setExpectReply(true);
        outbound.setRoutingKey(ADDRESS_QUEUE);
        return outbound;
    }

    @MessagingGateway(defaultRequestChannel = "fooAddressChannel")
    public interface AddressGateway {
        Message getAddress(Long userId);
    }
}
