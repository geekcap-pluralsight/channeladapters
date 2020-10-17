package com.globomantics.jdbcexample.config;

import com.globomantics.jdbcexample.model.Reservation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jdbc.JdbcMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.sql.DataSource;

@Configuration
public class JdbcOutboundConfig {
    @Bean
    public MessageChannel createReservationChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "createReservationChannel")
    public MessageHandler jdbcReservationMessageHandler(DataSource dataSource) {
        JdbcMessageHandler jdbcMessageHandler = new JdbcMessageHandler(dataSource,
                "INSERT INTO reservation (id, name, status) VALUES (?, ?, 0)");
        jdbcMessageHandler.setPreparedStatementSetter((ps, message) -> {
            Reservation reservation = (Reservation) message.getPayload();
            ps.setLong(1, reservation.getId());
            ps.setString(2, reservation.getName());
        });
        return jdbcMessageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "createReservationChannel")
    public interface CreateReservationGateway {
        void createReservation(Reservation reservation);
    }
}
