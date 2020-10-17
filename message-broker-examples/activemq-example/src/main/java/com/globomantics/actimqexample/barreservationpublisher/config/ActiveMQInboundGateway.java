package com.globomantics.actimqexample.barreservationpublisher.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsInboundGateway;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.messaging.MessageChannel;

import javax.jms.ConnectionFactory;

@Configuration
public class ActiveMQInboundGateway {
    private static final String QUEUE_NAME = "get-address-queue";
    @Bean public MessageChannel getAddressInboundGatewayChannel() {
        return new DirectChannel();
    }

    @Bean public JmsInboundGateway inboundGateway(
            @Qualifier("addressSimpleMessageListenerContainer") SimpleMessageListenerContainer container,
            @Qualifier("addressChannelPublishingJmsMessageListener") ChannelPublishingJmsMessageListener listener) {
        JmsInboundGateway gateway = new JmsInboundGateway(container, listener);
        gateway.setRequestChannel(getAddressInboundGatewayChannel());
        return gateway;
    }
    @Bean
    public SimpleMessageListenerContainer addressSimpleMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName(QUEUE_NAME);
        return container;
    }
    @Bean public ChannelPublishingJmsMessageListener addressChannelPublishingJmsMessageListener() {
        ChannelPublishingJmsMessageListener listener = new ChannelPublishingJmsMessageListener();
        listener.setExpectReply(true);
        return listener;
    }
}
