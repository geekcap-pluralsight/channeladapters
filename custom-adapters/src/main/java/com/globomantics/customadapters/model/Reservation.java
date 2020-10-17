package com.globomantics.customadapters.model;

public class Reservation {
    private int id;
    private String name;
    private String status;

    public Reservation() {
    }

    public Reservation(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Reservation setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Reservation setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Reservation setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
