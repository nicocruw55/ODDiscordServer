package com.odfin.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerFacade extends Remote {

    MessageFacade getMessageFacade() throws RemoteException;
    UserFacade getUserFacade() throws RemoteException;

}
