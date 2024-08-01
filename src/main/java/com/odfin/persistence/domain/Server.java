package com.odfin.persistence.domain;

import java.io.Serializable;
import java.util.Objects;

public class Server implements Serializable {

    int id;
    String name;

    public Server(String id, String name) {
        this.id = Integer.parseInt(id);
        this.name = name;
    }

    public Server() {}

    public Server(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return id == server.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
