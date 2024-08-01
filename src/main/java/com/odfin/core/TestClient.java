package com.odfin.core;

import com.odfin.facade.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TestClient {
    public static void main(String[] args) throws RemoteException, NotBoundException, UnknownHostException {
        System.out.println("Starte TestClient...");

        // Server RMI
        Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
        ServerFacade serverFacade = (ServerFacade) registry.lookup(ServerFacade.class.getSimpleName()); 
        System.out.println("found server facade: " + serverFacade);

        // Client remote object
        ClientFacadeImpl clientFacade = new ClientFacadeImpl();
        ClientFacade stub = (ClientFacade) UnicastRemoteObject.exportObject(clientFacade, 0);
        serverFacade.registerClient(stub);
        System.out.println("client registered");

        // get some facades
        UserFacade userFacade = serverFacade.getUserFacade();
        MessageFacade messageFacade = serverFacade.getMessageFacade();
        ChannelFacade channelFacade = serverFacade.getChannelFacade();

        // test facades
        messageFacade.sendMessage("Dennis macht Pipi auf Nico", 2, 2);
        System.out.println(userFacade.getAllUsers());
        System.out.println(messageFacade.getAllMessagesByChannelId(1));
        System.out.println(channelFacade.getAllChannelsByUserId(1));


    }
}
