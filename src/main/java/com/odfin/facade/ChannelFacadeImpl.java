package com.odfin.facade;

import com.odfin.persistence.dao.ChannelDAO;
import com.odfin.persistence.domain.Channel;
import com.odfin.persistence.factory.DAOFactory;

import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class ChannelFacadeImpl extends UnicastRemoteObject implements ChannelFacade {

    private final ChannelDAO channelDAO;

    public ChannelFacadeImpl() throws RemoteException {
        super(65300);
        this.channelDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getChatDAO();
    }

    @Override
    public Channel getChannelById(int chatId) throws RemoteException {
        try {
            return channelDAO.getChannelbyId(chatId);
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving chat", e);
        }
    }
    @Override
    public List<Channel> getAllChannels() throws RemoteException {
        try {
            return channelDAO.getAllChannels();
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving all chats", e);
        }
    }

    @Override
    public List<Channel> getAllChannelsByUserId(Integer userId) throws RemoteException {
        try {
            return channelDAO.getAllChannelsByUserId(userId);
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving all chats", e);
        }
    }

    @Override
    public void updateChannel(Channel channel) throws RemoteException {
        try {
            channelDAO.updateChannel(channel);
        } catch (SQLException e) {
            throw new RemoteException("Error updating chat", e);
        }
    }

    @Override
    public void insertChannel(Channel channel) throws RemoteException {
        try {
            channelDAO.insertChannel(channel);
        } catch (SQLException e) {
            throw new RemoteException("Error inserting chat", e);
        }
    }

    @Override
    public void deleteChannel(int chatId) throws RemoteException {
        try {
            channelDAO.deleteChannel(chatId);
        } catch (SQLException e) {
            throw new RemoteException("Error deleting chat", e);
        }
    }
}

