package com.globomantics.mongodbexample.MongoDBExample.config;

import com.globomantics.mongodbexample.MongoDBExample.model.Reservation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mongodb.outbound.MongoDbStoringMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class OutboundChannelAdapterConfig {
    @Bean
    public MessageChannel reservationToMongoChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel updateReservationChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "reservationToMongoChannel")
    public MessageHandler mongoMessageHandler(MongoTemplate template) {
        MongoDbStoringMessageHandler handler = new MongoDbStoringMessageHandler(template);
        handler.setCollectionNameExpression(new LiteralExpression("reservations"));
        return handler;
    }

    @MessagingGateway(defaultRequestChannel = "reservationToMongoChannel")
    public interface MongoReservationGateway {
        void publishReservation(Reservation reservation);
    }

    @Bean
    @ServiceActivator(inputChannel = "updateReservationChannel")
    public MessageHandler updateReservationMessageHandler(MongoTemplate template) {
        return message -> {
            Reservation reservation = (Reservation)message.getPayload();
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(reservation.getId()));
            Update update = new Update();
            update.set("name", reservation.getName());
            update.set("status", reservation.getStatus());
            template.updateFirst(query, update, Reservation.class);
        };
    }


    @MessagingGateway(defaultRequestChannel = "updateReservationChannel")
    public interface UpdateReservationGateway {
        void updateReservation(Reservation reservation);
    }
}
