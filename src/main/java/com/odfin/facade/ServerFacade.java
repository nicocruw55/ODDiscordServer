package com.odfin.facade;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerFacade extends Remote {

    MessageFacade getMessageFacade() throws RemoteException;
    UserFacade getUserFacade() throws RemoteException;
    ChannelFacade getChannelFacade() throws RemoteException;
    VoiceChatFacade getVoiceChatFacade() throws RemoteException;
    ScreenShareFacade getScreenShareFacade() throws RemoteException;

}
