package com.globomantics.actimqexample.barreservationpublisher;

import com.globomantics.actimqexample.barreservationpublisher.model.BarReservation;
import com.globomantics.actimqexample.barreservationpublisher.service.BarReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ActiveMQSampleApplication implements CommandLineRunner {

    @Autowired
    private BarReservationService service;

    @Override
    public void run(String... args) throws Exception {
        List<BarReservation> reservations = Arrays.asList(
                new BarReservation("1", "User 1"),
                new BarReservation("2", "User 2"),
                new BarReservation("3", "User 3"),
                new BarReservation("4", "User 4"),
                new BarReservation("5", "User 5"),
                new BarReservation("6", "User 6"));

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
        SpringApplication.run(ActiveMQSampleApplication.class, args);
    }

}
