package com.geekcap.pluralsight.spring5mvcexample.web;

import com.geekcap.pluralsight.spring5mvcexample.model.Reservation;
import com.geekcap.pluralsight.spring5mvcexample.service.ReservationService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class ReservationController {
    private static final Logger logger = LogManager.getLogger(ReservationController.class);

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservation/{id}")
    public ResponseEntity<?> getReservation(@PathVariable Long id) {
        return reservationService.findById(id)
                .map(reservation -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .eTag(Integer.toString(reservation.getVersion()))
                                .location(new URI("/reservation/" + reservation.getId()))
                                .body(reservation);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        try {
            return ResponseEntity.ok()
                    .location((new URI("/reservations")))
                    .body(reservationService.findAll());
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reservation")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        logger.info("Received reservation: name: " + reservation.getName());
        reservation.setStatus("None");
        reservation.setVersion(0);
        Reservation newReservation = reservationService.save(reservation);

        try {
            return ResponseEntity
                    .created(new URI("/reservation/" + newReservation.getId()))
                    .eTag(Integer.toString(newReservation.getVersion()))
                    .body(newReservation);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation reservation, @PathVariable Long id, @RequestHeader(HttpHeaders.IF_MATCH) String ifMatch) {
        // Get the widget with the specified id
        Optional<Reservation> existingWidget = reservationService.findById(id);
        if (!existingWidget.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Validate that the if-match header matches the widget's version
        if (!ifMatch.equalsIgnoreCase(Integer.toString(existingWidget.get().getVersion()))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Update the widget
        reservation.setId(id);
        reservation.setVersion(reservation.getVersion() + 1);
        reservation = reservationService.save(reservation);

        try {
            // Return a 200 response with the updated widget
            return ResponseEntity
                    .ok()
                    .eTag(Integer.toString(reservation.getVersion()))
                    .location(new URI("/reservation/" + reservation.getId()))
                    .body(reservation);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
