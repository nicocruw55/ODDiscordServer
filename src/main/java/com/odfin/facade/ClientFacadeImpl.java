package com.odfin.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientFacadeImpl extends UnicastRemoteObject implements ClientFacade{

    public ClientFacadeImpl() throws RemoteException {
        super(10000);
    }

    @Override
    public void receiveNotification(String message) throws RemoteException {
        System.out.println("Client message: " + message);
    }
}
