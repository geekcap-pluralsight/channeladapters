package com.globomantics.rabbitmqexample.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.inbound.AmqpInboundGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.json.JsonOutboundMessageMapper;
import org.springframework.messaging.MessageChannel;

@Configuration
public class FooAddressConfig {
    static final String ADDRESS_QUEUE = "get-address";
    static final String ADDRESS_REPLY_QUEUE = "get-address-reply";

    @Bean public MessageChannel getAddressInputChannel() {
        return new DirectChannel();
    }
    @Bean Queue addressQueue() {
        return new Queue(ADDRESS_QUEUE, false);
    }

    @Bean
    public AmqpInboundGateway inbound(SimpleMessageListenerContainer listenerContainer,
                                      @Qualifier("getAddressInputChannel") MessageChannel channel) {
        AmqpInboundGateway gateway = new AmqpInboundGateway(listenerContainer);
        gateway.setRequestChannel(channel);
        gateway.setDefaultReplyTo(ADDRESS_REPLY_QUEUE);
        return gateway;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(ADDRESS_QUEUE);
        container.setConcurrentConsumers(2);
        return container;
    }
}
