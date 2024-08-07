package com.odfin.facade;

import com.odfin.persistence.dao.MessageDAO;
import com.odfin.persistence.domain.Message;
import com.odfin.persistence.factory.DAOFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class MessageFacadeImpl extends UnicastRemoteObject implements MessageFacade {

    private final MessageDAO messageDAO;

    public MessageFacadeImpl() throws RemoteException {
        super(65300);
        this.messageDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getMessageDAO();
    }

    @Override
    public Message getMessageById(int messageId) throws RemoteException {
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
    public List<Message> getAllMessagesByChannelId(int channelId) throws RemoteException {
        try {
            return messageDAO.getAllMessagesByChannelId(channelId);
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving all messages", e);
        }
    }

    @Override
    public List<Message> getMessagesByChannelIdLazyLoading(int channelId, Message lastMessage, int count) throws RemoteException {
        try {
            return messageDAO.getMessagesByChannelIdLazyLoading(channelId, lastMessage, count);
        } catch (SQLException e) {
            throw new RemoteException("Error lazy loading messages", e);
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
    public void deleteMessage(int messageId) throws RemoteException {
        try {
            messageDAO.deleteMessage(messageId);
        } catch (SQLException e) {
            throw new RemoteException("Error deleting message", e);
        }
    }

    @Override
    public Message sendMessage(String content, int senderId, int channelId) throws RemoteException {
        try {
            return messageDAO.sendMessage(content, senderId, channelId);
        } catch (SQLException e) {
            throw new RemoteException("Error sending message", e);
        }
    }


}
