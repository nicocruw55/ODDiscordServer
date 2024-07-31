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

        // Client RMI
        ClientFacadeImpl clientFacade = new ClientFacadeImpl();
        ClientFacade stub = (ClientFacade) UnicastRemoteObject.exportObject(clientFacade, 99);
        Registry clientRegistry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT );
        clientRegistry.rebind(ClientFacade.class.getSimpleName(), stub);
        System.out.println("Client-Registry gestartet und Client-Service registriert.");

        // Server RMI
        Registry registry = LocateRegistry.getRegistry("cruw-community.de", Registry.REGISTRY_PORT);
        ServerFacade serverFacade = (ServerFacade) registry.lookup(ServerFacade.class.getSimpleName());
        System.out.println("ServerFacade gefunden: " + serverFacade);

        // register test
        serverFacade.registerClient(clientFacade);
        //String localIp = InetAddress.getLocalHost().getHostAddress();
        //int localPort = Registry.REGISTRY_PORT;
        //serverFacade.registerClient2(localIp, localPort);

        // Hole die Facades vom Server
        UserFacade userFacade = serverFacade.getUserFacade();
        MessageFacade messageFacade = serverFacade.getMessageFacade();
        ChannelFacade channelFacade = serverFacade.getChannelFacade();

        // Führe Operationen aus
        messageFacade.sendMessage("Dennis macht Pipi auf Nico", 2, 2);
        System.out.println(userFacade.getAllUsers());
        System.out.println(messageFacade.getAllMessagesByChannelId(1));
        System.out.println(channelFacade.getAllChannelsByUserId(1));


    }
}
