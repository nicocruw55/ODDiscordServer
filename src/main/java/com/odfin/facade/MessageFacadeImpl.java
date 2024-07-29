package com.odfin.facade;


import com.odfin.persistence.dao.MessageDAO;
import com.odfin.persistence.domain.Message;
import com.odfin.persistence.impl.MySqlMessageDAO;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class MessageFacadeImpl implements MessageFacade {

    private final MessageDAO messageDAO;

    public MessageFacadeImpl() throws RemoteException {
        super();
        this.messageDAO = new MySqlMessageDAO();
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        try {
            messageDAO.createMessage(message);
        } catch (SQLException e) {
            throw new RemoteException("Error sending message", e);
        }
    }

    @Override
    public Message getMessageById(Long messageId) throws RemoteException {
        try {
            return messageDAO.getMessageById(messageId);
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving message", e);
        }
    }

    @Override
    public List<Message> getAllMessages() throws RemoteException {
        try {
            return messageDAO.getAllMessages();
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving all messages", e);
        }
    }

    @Override
    public void updateMessage(Message message) throws RemoteException {
        try {
            messageDAO.updateMessage(message);
        } catch (SQLException e) {
            throw new RemoteException("Error updating message", e);
        }
    }

    @Override
    public void deleteMessage(Long messageId) throws RemoteException {
        try {
            messageDAO.deleteMessage(messageId);
        } catch (SQLException e) {
            throw new RemoteException("Error deleting message", e);
        }
    }
}
