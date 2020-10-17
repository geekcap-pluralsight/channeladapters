package com.globomantics.httpexample.model;

import java.io.Serializable;

public class Reservation implements Serializable {
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
