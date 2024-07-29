package com.odfin.persistence.dao;


import com.odfin.persistence.domain.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {

    void createMessage(Message message) throws SQLException;

    Message getMessageById(Long messageId) throws SQLException;

    List<Message> getAllMessages() throws SQLException;

    void updateMessage(Message message) throws SQLException;

    void deleteMessage(Long messageId) throws SQLException;
}
