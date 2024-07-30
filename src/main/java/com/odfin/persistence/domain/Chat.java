package com.odfin.persistence.domain;

import java.util.List;

public class Chat {

    private int chatID;
    private String chatName;
    private List<ChatMember> members;
    private List<Message> messages;

    public Chat() {}

    public Chat(int chatID, String chatName, List<ChatMember> members, List<Message> messages) {
        this.chatID = chatID;
        this.chatName = chatName;
        this.members = members;
        this.messages = messages;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public List<ChatMember> getMembers() {
        return members;
    }

    public void setMembers(List<ChatMember> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
