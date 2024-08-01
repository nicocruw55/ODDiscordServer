package com.odfin.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VoiceChatFacade extends Remote {

    int[] getAllCallersFromChannelId(int channelId) throws RemoteException;

}
