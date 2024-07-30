package com.odfin.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerFacadeImpl implements ServerFacade{

    private static MessageFacade messageFacade;
    private static UserFacade userFacade;
    private static ChannelFacade channelFacade;

    public ServerFacadeImpl() throws RemoteException {
        super();
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

    @Override
    public ChannelFacade getChannelFacade() throws RemoteException {
        if(channelFacade == null)
            channelFacade = new ChannelFacadeImpl();

        return channelFacade;
    }
}
