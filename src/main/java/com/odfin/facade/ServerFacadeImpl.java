package com.odfin.facade;

import java.io.Serializable;
import java.rmi.RemoteException;

public class ServerFacadeImpl implements ServerFacade{

    private static MessageFacade messageFacade;
    private static UserFacade userFacade;

    public ServerFacadeImpl() throws RemoteException {
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
