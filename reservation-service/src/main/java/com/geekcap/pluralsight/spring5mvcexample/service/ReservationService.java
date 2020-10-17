package com.geekcap.pluralsight.spring5mvcexample.service;

import com.geekcap.pluralsight.spring5mvcexample.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Optional<Reservation> findById(Long id);
    List<Reservation> findAll();
    Reservation save(Reservation widget);
    void deleteById(Long id);
}
