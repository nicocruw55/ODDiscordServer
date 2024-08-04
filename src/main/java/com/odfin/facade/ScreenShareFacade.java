package com.odfin.facade;

import com.odfin.persistence.domain.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ScreenShareFacade extends Remote {

    List<User> getScreenSharingUsersByChannelId(int channelId) throws RemoteException;
    boolean isUserStreamingInChannel(int userId, int channelId) throws RemoteException;

}
