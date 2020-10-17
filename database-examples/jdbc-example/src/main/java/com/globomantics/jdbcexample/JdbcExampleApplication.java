package com.globomantics.jdbcexample;

import com.globomantics.jdbcexample.model.Reservation;
import com.globomantics.jdbcexample.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class JdbcExampleApplication implements CommandLineRunner {

    @Autowired
    private ReservationService service;

    public static void main(String[] args) {
        SpringApplication.run(JdbcExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Reservation> reservations = Arrays.asList(
                new Reservation(1L, "User 1"),
                new Reservation(2L, "User 2"),
                new Reservation(3L, "User 3"),
                new Reservation(4L, "User 4"),
                new Reservation(5L, "User 5"));
        reservations.forEach(reservation -> {
        	service.createReservation(reservation);
        	try {
        		Thread.sleep(1000);
			} catch (InterruptedException e) {
        		e.printStackTrace();
			}
		});
    }
}
