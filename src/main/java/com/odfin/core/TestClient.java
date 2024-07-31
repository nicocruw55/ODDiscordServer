package com.odfin.core;

import com.odfin.facade.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {
    public static void main(String[] args) {
        try {
            // Erstelle das Client-Remote-Objekt
            ClientFacadeImpl clientFacade = new ClientFacadeImpl();

            // Registriere das Client-Remote-Objekt beim RMI-Registry des Clients
            Registry clientRegistry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
            clientRegistry.rebind("//localhost/ClientService", clientFacade);

            // Suche das Server-Remote-Objekt
            Registry serverRegistry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
            ServerFacade serverFacade = (ServerFacade) serverRegistry.lookup(ServerFacade.class.getSimpleName());

            // Registriere das Client-Remote-Objekt beim Server
            serverFacade.registerClient(clientFacade);

            UserFacade userFacade = serverFacade.getUserFacade();
            MessageFacade messageFacade = serverFacade.getMessageFacade();
            ChannelFacade channelFacade = serverFacade.getChannelFacade();

            messageFacade.sendMessage("Dennis macht Pipi auf Nico", 2, 2);

            System.out.println(userFacade.getAllUsers());
            System.out.println(messageFacade.getAllMessagesByChannelId(1));
            System.out.println(channelFacade.getAllChannelsByUserId(1));

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
