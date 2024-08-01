package com.odfin.core;

import com.odfin.core.Notification.NotificationServer;
import com.odfin.facade.ServerFacade;
import com.odfin.facade.ServerFacadeImpl;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MessengerServer {

    public static void main(String[] args) throws IOException {

        // notification server on seperate thread
        new Thread(() -> {
            while (true) {
                try {
                    new NotificationServer();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        System.setProperty("java.rmi.server.hostname", "localhost");
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        ServerFacadeImpl serverFacadeImpl = new ServerFacadeImpl();
        registry.rebind(ServerFacade.class.getSimpleName(), serverFacadeImpl);
        System.out.println("Messenger RMI registry created...");
    }

}
