package com.odfin.persistence.dao;


import com.odfin.persistence.domain.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {


    Message getMessageById(Integer messageId) throws SQLException;
    List<Message> getAllMessages() throws SQLException;
    void updateMessage(Message message) throws SQLException;
    void deleteMessage(Integer messageId) throws SQLException;
}
