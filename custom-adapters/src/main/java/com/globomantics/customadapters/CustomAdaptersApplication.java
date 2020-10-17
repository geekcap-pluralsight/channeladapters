package com.globomantics.customadapters;

import java.util.Arrays;
import java.util.List;

import com.globomantics.customadapters.model.Reservation;
import com.globomantics.customadapters.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CustomAdaptersApplication implements CommandLineRunner, ExitCodeGenerator {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private ReservationService service;

    public static void main(String[] args) {
        SpringApplication.run(CustomAdaptersApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Reservation> reservations = Arrays.asList(
                new Reservation(1, "User 1", "None"),
                new Reservation(2, "User 2", "None"),
                new Reservation(3, "User 3", "None"),
                new Reservation(4, "User 4", "None"),
                new Reservation(5, "User 5", "None"),
                new Reservation(6, "User 6", "None"));
        reservations.forEach(reservation -> {
            service.createReservation(reservation);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SpringApplication.exit(context, this);
    }

    @Override
    public int getExitCode() {
        return 0;
    }
}
