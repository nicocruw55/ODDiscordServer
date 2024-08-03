package com.odfin.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VoiceChatFacade extends Remote {

    int[] getVoiceUsersByChannelId(int channelId) throws RemoteException;
    boolean isUserInVoiceChannel(int userId, int channelId) throws RemoteException;

}
