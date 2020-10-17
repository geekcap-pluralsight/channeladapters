package com.geekcap.pluralsight.spring5mvcexample.repository;

import com.geekcap.pluralsight.spring5mvcexample.model.Reservation;

import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
}
