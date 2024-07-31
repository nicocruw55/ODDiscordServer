package com.odfin.facade;

import java.rmi.NotBoundException;
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

    public void registerClient(ClientFacade client) throws RemoteException {
        System.out.println("Registering client...");
        if (client != null) {
            clients.add(client);
            System.out.println("Client registered.");
            notifyClients("Hello Client!!");
        }
    }

    public boolean registerClient2(String ip, int port) throws RemoteException, NotBoundException {
        System.out.println("registering...");
        System.out.println("ip: " + ip);
        System.out.println("port: " + port);
        System.out.println("getting registry...");
        Registry registry = LocateRegistry.getRegistry(ip, port);
        System.out.println("registry lookup...");
        ClientFacade client = (ClientFacade) registry.lookup(ClientFacade.class.getSimpleName());
        clients.add(client);
        notifyClients("Hello Client!!");
        return true;
    }

    public void unregisterClient(ClientFacade client) throws RemoteException {
        if (client != null) {
            clients.remove(client);
            System.out.println("Client unregistered.");
        }
    }

    public void notifyClients(String message) throws RemoteException {
        System.out.println("Sending message to client: " + message);
        for (ClientFacade client : clients) {
            try {
                client.receiveNotification(message);
            } catch (RemoteException e) {
                System.out.println("Failed to notify client: " + e.getMessage());
            }
        }
    }
}
