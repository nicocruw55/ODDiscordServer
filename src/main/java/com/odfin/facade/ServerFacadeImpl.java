package com.odfin.facade;

import java.rmi.RemoteException;

public class ServerFacadeImpl implements ServerFacade {

    private static MessageFacade messageFacade;
    private static UserFacade userFacade;


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
