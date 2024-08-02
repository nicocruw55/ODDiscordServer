package com.odfin.facade;

import com.odfin.persistence.dao.ChannelDAO;
import com.odfin.persistence.dao.ChannelGroupDAO;
import com.odfin.persistence.domain.ChannelGroup;
import com.odfin.persistence.factory.DAOFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChannelGroupFacadeImpl extends UnicastRemoteObject implements ChannelGroupFacade {

    private final ChannelDAO ChannelGroupDAO;

    public ChannelGroupFacadeImpl() throws RemoteException {
        super(65300);
        this.ChannelGroupDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getChannelGroupDAO();
    }
p
    @Override
    public ChannelGroup getChannelGroupById(int chatId) {
        return null;
    }

    @Override
    public List<ChannelGroup> getAllChannelGroups() {
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

    priva

}
