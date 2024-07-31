package com.odfin.core;

import com.odfin.facade.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        System.out.println("Starte TestClient...");

        // Verbindung zum RMI-Registry des Servers
        Registry registry = LocateRegistry.getRegistry("cruw-community.de", Registry.REGISTRY_PORT);
        ServerFacade serverFacade = (ServerFacade) registry.lookup(ServerFacade.class.getSimpleName());

        System.out.println("ServerFacade gefunden: " + serverFacade);

        // Erstelle und registriere das Client-Remote-Objekt
        ClientFacadeImpl clientFacade = new ClientFacadeImpl();
        serverFacade.registerClient(clientFacade);

        System.out.println("ClientFacade erfolgreich beim Server registriert.");

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
