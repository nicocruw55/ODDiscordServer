package com.odfin.persistence.domain;

import java.io.Serializable;
import java.util.Objects;

public class ChannelGroup implements Serializable {

    int id;
    String name;

    public ChannelGroup(String id, String name) {
        this.id = Integer.parseInt(id);
        this.name = name;
    }

    public ChannelGroup() {}

    public ChannelGroup(int id, String name) {
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
        ChannelGroup channelGroup = (ChannelGroup) o;
        return id == channelGroup.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ChannelGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
