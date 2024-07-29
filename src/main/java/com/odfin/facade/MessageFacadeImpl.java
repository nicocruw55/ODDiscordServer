package com.odfin.facade;


import com.odfin.persistence.dao.MessageDAO;
import com.odfin.persistence.domain.Message;
import com.odfin.persistence.impl.MySqlMessageDAO;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class MessageFacadeImpl extends UnicastRemoteObject implements MessageFacade {

    private final MessageDAO messageDAO;

    public MessageFacadeImpl() throws RemoteException {
        super();
        this.messageDAO = new MySqlMessageDAO();
    }

    @Override
    public Message getMessageById(Integer messageId) throws RemoteException {
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
    public void deleteMessage(Integer messageId) throws RemoteException {
        try {
            messageDAO.deleteMessage(messageId);
        } catch (SQLException e) {
            throw new RemoteException("Error deleting message", e);
        }
    }
}
