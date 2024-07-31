package com.odfin.core;

import com.odfin.facade.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {
    public static void main(String[] args) {
        try {
            // Suche das Server-Remote-Objekt im RMI-Registry
            Registry registry = LocateRegistry.getRegistry("cruw-community.de", Registry.REGISTRY_PORT);
            ServerFacade serverFacade = (ServerFacade) registry.lookup("ServerFacade");

            // Erstelle das Client-Remote-Objekt
            ClientFacadeImpl clientFacade = new ClientFacadeImpl();

            // Registriere das Client-Remote-Objekt beim Server
            serverFacade.registerClient(clientFacade);

            // Hole die Facades vom Server
            UserFacade userFacade = serverFacade.getUserFacade();
            MessageFacade messageFacade = serverFacade.getMessageFacade();
            ChannelFacade channelFacade = serverFacade.getChannelFacade();

            // Sende eine Nachricht über die MessageFacade
            messageFacade.sendMessage("Dennis macht Pipi auf Nico", 2, 2);

            // Gebe einige Informationen aus
            System.out.println(userFacade.getAllUsers());
            System.out.println(messageFacade.getAllMessagesByChannelId(1));
            System.out.println(channelFacade.getAllChannelsByUserId(1));

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
