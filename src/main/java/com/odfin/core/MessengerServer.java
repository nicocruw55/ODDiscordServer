package com.odfin.core;


import com.odfin.facade.ServerFacade;
import com.odfin.facade.ServerFacadeImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

public class MessengerServer {

    public static void main(String[] args) {

        try {
            System.setProperty("java.rmi.server.hostname", "localhost");
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

            ServerFacade serverFacade = new ServerFacadeImpl();

            RemoteServer.setLog(System.out);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(ServerFacade.class.getSimpleName(), serverFacade);

            System.out.println("Started registry...");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

}
