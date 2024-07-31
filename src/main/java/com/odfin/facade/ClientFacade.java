package com.odfin.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientFacade extends Remote {
    void receiveNotification(String message) throws RemoteException;
}
