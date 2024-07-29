package com.odfin.persistence.dao;


import com.odfin.persistence.domain.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {

    Message getMessageById(Integer messageId) throws SQLException;
    List<Message> getAllMessages() throws SQLException;
    Message updateMessage(Message message) throws SQLException;
    Message insertMessage(Message message) throws SQLException;
    boolean deleteMessage(Integer messageId) throws SQLException;

}
