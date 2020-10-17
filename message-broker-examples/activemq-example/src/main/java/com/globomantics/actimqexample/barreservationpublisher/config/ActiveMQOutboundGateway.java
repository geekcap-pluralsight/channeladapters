package com.globomantics.actimqexample.barreservationpublisher.config;

import com.globomantics.actimqexample.barreservationpublisher.model.BarAddress;
import com.globomantics.actimqexample.barreservationpublisher.model.BarReservation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.jms.JmsOutboundGateway;
import org.springframework.messaging.MessageChannel;

import javax.jms.ConnectionFactory;

@Configuration
public class ActiveMQOutboundGateway {
    private static final String REQUEST_QUEUE_NAME = "get-address-queue";
    private static final String REPLY_QUEUE_NAME = "get-address-reply-queue";

    @Bean
    public MessageChannel getAddressChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel getAddressReplyChannel() {
        return new QueueChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "getAddressChannel")
    public JmsOutboundGateway outboundGateway(ConnectionFactory connectionFactory) {
        JmsOutboundGateway gateway = new JmsOutboundGateway();
        gateway.setConnectionFactory(connectionFactory);
        gateway.setRequestDestinationName(REQUEST_QUEUE_NAME);
        gateway.setReplyDestinationName(REPLY_QUEUE_NAME);
        gateway.setReplyChannel(getAddressReplyChannel());
        return gateway;
    }

    @MessagingGateway(defaultRequestChannel = "getAddressChannel", defaultReplyChannel = "getAddressReplyChannel")
    public interface AddressGateway {
        String getAddress(String userId);
    }
}
