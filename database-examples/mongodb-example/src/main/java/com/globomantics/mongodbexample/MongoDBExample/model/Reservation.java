package com.globomantics.mongodbexample.MongoDBExample.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservations")
public class Reservation {
    private ObjectId id;
    private String name;
    private String status;

    public Reservation() {
    }

    public Reservation(ObjectId id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Reservation(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
