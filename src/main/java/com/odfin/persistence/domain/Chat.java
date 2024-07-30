package com.odfin.persistence.domain;

import java.util.List;

public class Chat {
    private int chatID;
    private String chatName;
    private List<ChatMember> members;
    private List<Message> messages;
}
