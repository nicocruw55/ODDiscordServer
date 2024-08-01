package com.odfin.facade;

import com.odfin.core.Notification.NotificationServer;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerFacadeImpl extends UnicastRemoteObject implements ServerFacade{

    private static MessageFacade messageFacade;
    private static UserFacade userFacade;
    private static ChannelFacade channelFacade;
    private List<ClientFacade> clients;

    public ServerFacadeImpl() throws RemoteException {
        super(65300);
        clients = new ArrayList<>();
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

    @Override
    public void registerClient(Remote client) throws RemoteException {
        System.out.println("Registering client...");
        if (client != null) {
            clients.add((ClientFacade) client);
        }
    }

    @Override
    public void unregisterClient(ClientFacade client) throws RemoteException {
        if (client != null) {
            clients.remove(client);
            System.out.println("Client unregistered.");
        }
    }

}
