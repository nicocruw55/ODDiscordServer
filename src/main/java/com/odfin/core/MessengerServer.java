package com.odfin.core;

import com.odfin.notification.NotificationServer;
import com.odfin.facade.ServerFacade;
import com.odfin.facade.ServerFacadeImpl;
import com.odfin.persistence.util.ServerHelper;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MessengerServer {

    public static void main(String[] args) throws IOException {
        new NotificationServer().start();
        System.setProperty("java.rmi.server.hostname", ServerHelper.SERVER_NAME);
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        ServerFacadeImpl serverFacadeImpl = new ServerFacadeImpl();
        registry.rebind(ServerFacade.class.getSimpleName(), serverFacadeImpl);
        System.out.println("Messenger RMI registry created...");
    }

}
