package com.globomantics.actimqexample.barreservationpublisher.model;

public class BarReservation {
    private String  id;
    private String name;

    public BarReservation() {
    }

    public BarReservation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BarReservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
