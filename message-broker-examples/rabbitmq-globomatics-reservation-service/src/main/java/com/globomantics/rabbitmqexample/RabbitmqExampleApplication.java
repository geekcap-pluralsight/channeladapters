package com.globomantics.rabbitmqexample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqExampleApplication implements CommandLineRunner {
	private static final Logger logger = LogManager.getLogger(RabbitmqExampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Starting Foo Reservation Listener");
	}
}
