package com.geekcap.pluralsight.spring5mvcexample.model;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String status;
    private int version;

    public Reservation() {
    }

    public Reservation(Long id, String name, String status, int version) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public Reservation setId(Long id) {
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

    public int getVersion() {
        return version;
    }

    public Reservation setVersion(int version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", version=" + version +
                '}';
    }
}
