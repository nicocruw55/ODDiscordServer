package com.odfin.persistence.domain;

import java.io.Serializable;

public class Channel implements Serializable {

    private int id;
    private String name;
    private String topic;

    public Channel() {}

    public Channel(int id, String name, String topic) {
        this.id = id;
        this.name = name;
        this.topic = topic;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
