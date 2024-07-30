package com.odfin.persistence.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message implements Serializable {

    private Integer id;
    private Integer senderId;
    private String content;
    private LocalDateTime timestamp;
    //private MessageType messageType;

    public Message() {
    }

    public Message(Integer id, Integer senderId, String content, LocalDateTime timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
        //this.messageType = messageType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
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

    //public MessageType getMessageType() {
    //    return messageType;
    //}

    //public void setMessageType(MessageType messageType) {
    //    this.messageType = messageType;
    //}

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
                //", messageType=" + messageType +
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
