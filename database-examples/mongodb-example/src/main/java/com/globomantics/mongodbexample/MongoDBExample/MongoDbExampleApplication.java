package com.globomantics.mongodbexample.MongoDBExample;

import com.globomantics.mongodbexample.MongoDBExample.model.Reservation;
import com.globomantics.mongodbexample.MongoDBExample.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MongoDbExampleApplication implements CommandLineRunner {
	@Autowired
	private ReservationService service;

	@Override
	public void run(String... args) throws Exception {
		List<Reservation> reservations = Arrays.asList(
				new Reservation("User 1", "None"),
				new Reservation("User 2", "None"),
				new Reservation("User 3", "None"),
				new Reservation("User 4", "None"),
				new Reservation("User 5", "None"),
				new Reservation("User 6", "None"));
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
		SpringApplication.run(MongoDbExampleApplication.class, args);
	}
}
