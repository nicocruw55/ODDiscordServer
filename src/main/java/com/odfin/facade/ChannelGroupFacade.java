package com.odfin.facade;

import com.odfin.persistence.domain.ChannelGroup;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChannelGroupFacade extends Remote {

    ChannelGroup getChannelGroupById(int chatId) throws RemoteException;
    List<ChannelGroup> getAllChannelGroups() throws RemoteException;
    List<ChannelGroup> getAllChannelGroupsByUserId(int userId) throws RemoteException;
    void updateChannelGroup(ChannelGroup channelGroup) throws RemoteException;
    void insertChannelGroup(ChannelGroup channelGroup) throws RemoteException;
    void deleteChannelGroup(int chatId) throws RemoteException;

}
