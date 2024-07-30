package com.odfin.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerFacadeImpl extends UnicastRemoteObject implements ServerFacade{

    private static MessageFacade messageFacade;
    private static UserFacade userFacade;

    public ServerFacadeImpl() throws RemoteException {
        super(65300);
    }

    @Override
    public MessageFacade getMessageFacade() throws RemoteException {
        if(messageFacade == null)
            messageFacade = new MessageFacadeImpl();

        return messageFacade;
    }

    @Override
    public UserFacade getUserFacade() throws RemoteException {
        if(userFacade == null)
            userFacade = new UserFacadeImpl();

        return userFacade;
    }
}
