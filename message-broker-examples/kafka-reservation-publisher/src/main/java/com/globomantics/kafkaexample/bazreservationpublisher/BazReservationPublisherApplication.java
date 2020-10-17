package com.globomantics.kafkaexample.bazreservationpublisher;

import com.globomantics.kafkaexample.bazreservationpublisher.model.BazReservation;
import com.globomantics.kafkaexample.bazreservationpublisher.service.BazReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BazReservationPublisherApplication implements CommandLineRunner {
    @Autowired
    private BazReservationService service;

    @Override
    public void run(String... args) throws Exception {
        List<BazReservation> reservations = Arrays.asList(
                new BazReservation(1, "User 1"),
                new BazReservation(2, "User 2"),
                new BazReservation(3, "User 3"),
                new BazReservation(4, "User 4"),
                new BazReservation(5, "User 5"),
                new BazReservation(6, "User 6"));
        reservations.forEach(reservation -> {
            service.publishReservation(reservation);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    public static void main(String[] args) {
        SpringApplication.run(BazReservationPublisherApplication.class, args);
    }
}
