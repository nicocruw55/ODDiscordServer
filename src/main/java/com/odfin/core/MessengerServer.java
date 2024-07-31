package com.odfin.core;

import com.odfin.facade.ServerFacade;
import com.odfin.facade.ServerFacadeImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MessengerServer {

    public static void main(String[] args) {
        try {
            // Setze den Hostnamen für die RMI-Server
            System.setProperty("java.rmi.server.hostname", "cruw-community.de");

            // Erstelle oder bekomme die RMI-Registry
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            System.out.println("RMI Registry started on port " + Registry.REGISTRY_PORT);

            // Erstelle die Implementierung des ServerFacade
            ServerFacadeImpl serverFacadeImpl = new ServerFacadeImpl();

            // Binde das ServerFacade-Objekt an die Registry
            registry.rebind(ServerFacade.class.getSimpleName(), serverFacadeImpl);

            System.out.println("ServerFacade bound to registry and ready.");

        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException("Server failed to start.", e);
        }
    }

}
