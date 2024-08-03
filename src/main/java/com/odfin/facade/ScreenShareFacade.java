package com.odfin.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ScreenShareFacade extends Remote {

    int[] getScreenSharingUsersByChannelId(int channelId) throws RemoteException;
    boolean isUserStreamingInChannel(int userId, int channelId) throws RemoteException;

}
