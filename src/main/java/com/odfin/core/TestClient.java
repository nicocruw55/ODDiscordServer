package com.odfin.core;

import com.odfin.core.Notification.NotificationClient;
import com.odfin.facade.*;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {

    public static void main(String[] args) throws IOException, NotBoundException {

        // Notification client on seperate thread
        new Thread(() -> {
            while (true) {
                try {
                    new NotificationClient(1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        System.out.println("Starting client...");

        // Server RMI
        Registry registry = LocateRegistry.getRegistry("cruw-community.de", Registry.REGISTRY_PORT);
        ServerFacade serverFacade = (ServerFacade) registry.lookup(ServerFacade.class.getSimpleName()); 
        System.out.println("found server facade: " + serverFacade);

        // get some facades
        UserFacade userFacade = serverFacade.getUserFacade();
        MessageFacade messageFacade = serverFacade.getMessageFacade();
        ChannelFacade channelFacade = serverFacade.getChannelFacade();

        // test facades
        messageFacade.sendMessage("KAKAKAIAKAIAKAIAKAIAKAIAK", 1, 1);

    }

}
