package com.geekcap.pluralsight.spring5mvcexample.service;

import com.geekcap.pluralsight.spring5mvcexample.model.Reservation;
import com.geekcap.pluralsight.spring5mvcexample.repository.ReservationRepository;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository repository;

    public ReservationServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Reservation> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Reservation save(Reservation widget) {
        // Increment the version number
        widget.setVersion(widget.getVersion()+1);

        // Save the widget to the repository
        return repository.save(widget);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
