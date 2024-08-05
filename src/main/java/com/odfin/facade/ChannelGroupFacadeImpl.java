package com.odfin.facade;

import com.odfin.persistence.dao.ChannelDAO;
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
    public List<ChannelGroup> getAllChannelGroups() {
        try {
            return ChannelGroupDAO.getChannelGroupsByUserID(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public List<ChannelGroup> getAllChannelGroupsByUserId(int userId) {
        return List.of();
    }

    @Override
    public void updateChannelGroup(ChannelGroup channelGroup) {

    }

    @Override
    public void insertChannelGroup(ChannelGroup channelGroup) {

    }

    @Override
    public void deleteChannelGroup(int chatId) {

    }


}
