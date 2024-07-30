package com.odfin.facade;

import com.odfin.persistence.domain.Channel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface ChannelFacade extends Remote {

    Channel getChannelById(int chatId) throws RemoteException;
    List<Channel> getAllChannels() throws RemoteException;
    List<Channel> getAllChannelsByUserId(Integer userId) throws RemoteException;
    void updateChannel(Channel channel) throws RemoteException;
    void insertChannel(Channel channel) throws RemoteException;
    void deleteChannel(int chatId) throws RemoteException;

}
