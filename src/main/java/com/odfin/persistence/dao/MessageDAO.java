package com.odfin.persistence.dao;


import com.odfin.persistence.domain.Message;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {

    Message getMessageById(int messageId) throws SQLException;
    List<Message> getAllMessages() throws SQLException;
    List<Message> getAllMessagesByChannelId(int channelId) throws SQLException; // for chat history
    List<Message> getMessagesByChannelIdLazyLoading(int channelId, Message lastMessage, int count) throws SQLException; // for chat history
    Message updateMessage(Message message) throws SQLException;
    Message insertMessage(Message message) throws SQLException;
    boolean deleteMessage(int messageId) throws SQLException;
    Message sendMessage(String content, int senderId, int channelId) throws SQLException;

}
