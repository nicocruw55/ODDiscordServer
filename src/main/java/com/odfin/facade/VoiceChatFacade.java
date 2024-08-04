package com.odfin.facade;

import com.odfin.persistence.domain.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VoiceChatFacade extends Remote {

    List<User> getVoiceUsersByChannelId(int channelId) throws RemoteException;
    boolean isUserInVoiceChannel(int userId, int channelId) throws RemoteException;

}
