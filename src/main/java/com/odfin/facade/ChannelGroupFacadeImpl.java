package com.odfin.facade;

import com.odfin.persistence.dao.ChannelGroupDAO;
import com.odfin.persistence.domain.ChannelGroup;
import com.odfin.persistence.factory.DAOFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class ChannelGroupFacadeImpl extends UnicastRemoteObject implements ChannelGroupFacade {

    private final ChannelGroupDAO ChannelGroupDAO;

    public ChannelGroupFacadeImpl() throws RemoteException {
        super(65300);
        this.ChannelGroupDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getChannelGroupDAO();
    }

    @Override
    public ChannelGroup getChannelGroupById(int chatId) throws RemoteException{
        try {
            return ChannelGroupDAO.getChannelGroupById(chatId);
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving channel group", e);
        }
    }


    @Override
    public List<ChannelGroup> getAllChannelGroupsByUserId(int userId) throws RemoteException {
        try {
            return ChannelGroupDAO.getChannelGroupsByUserID(userId);
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving all channel group by UserID", e);
        }
    }

    @Override
    public void updateChannelGroup(ChannelGroup channelGroup) throws RemoteException {
        try {
            ChannelGroupDAO.updateChannelGroup(channelGroup);
        } catch (SQLException e) {
            throw new RemoteException("Error updating channel group", e);
        }
    }

    @Override
    public ChannelGroup createChannelGroup(ChannelGroup channelGroup, int userID) throws RemoteException {
        try {
            return ChannelGroupDAO.createChannelGroup(channelGroup, userID);
        } catch (SQLException e) {
           throw new RemoteException("Error inserting channel group", e);
        }
    }

    @Override
    public boolean deleteChannelGroup(int chatId) throws RemoteException {
        try {
            return ChannelGroupDAO.deleteChannelGroup(chatId);
        } catch (SQLException e) {
            throw new RemoteException("Error deleting channel group", e);
        }
    }
}
