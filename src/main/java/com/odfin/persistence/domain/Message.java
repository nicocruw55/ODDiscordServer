package com.odfin.persistence.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message implements Serializable {

    private Long id;
    private User sender;
    private Long chatId;
    private String content;
    private LocalDateTime timestamp;
    private MessageType messageType;

    public Message(long aLong, User userById, long aLong1, String string, LocalDateTime localDateTime, MessageType messageType) {

    }

    public Message() {
    }

    public Message(Long id, User sender, Long chatId, String content, LocalDateTime timestamp, MessageType messageType) {
        this.id = id;
        this.sender = sender;
        this.chatId = chatId;
        this.content = content;
        this.timestamp = timestamp;
        this.messageType = messageType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
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
                ", sender=" + sender +
                ", chatId=" + chatId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", messageType=" + messageType +
                '}';
    }
}
