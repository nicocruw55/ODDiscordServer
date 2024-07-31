package com.odfin.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerFacade extends Remote {

    MessageFacade getMessageFacade() throws RemoteException;
    UserFacade getUserFacade() throws RemoteException;
    ChannelFacade getChannelFacade() throws RemoteException;
    void registerClient(ClientFacade client) throws RemoteException;
    void unregisterClient(ClientFacade client) throws RemoteException;
    boolean registerClient2() throws RemoteException;

}
