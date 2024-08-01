package com.odfin.persistence.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message implements Serializable {

    private int id;
    private int senderId;
    private int channelId;
    private String content;
    private String name;
    private LocalDateTime timestamp;

    public Message() {
    }

    public Message(int id, int senderId, int channelId, String content, String name, LocalDateTime timestamp) {
        this.id = id;
        this.channelId = channelId;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int chatId) {
        this.channelId = chatId;
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
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + senderId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", name =" + name +
                '}';
    }

    enum MessageType {
        TEXT,
        IMAGE,
        FILE,
        VIDEO,
        OTHER
    }

}
