package com.odfin.core;

import com.odfin.core.Notification.NotificationClient;
import com.odfin.core.Notification.NotificationClientHandler;
import com.odfin.core.Notification.NotificationServer;
import com.odfin.facade.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TestClient {

    public static void main(String[] args) throws IOException, NotBoundException {

        // Notification client on seperate thread
        new Thread(() -> {
            while (true) {
                try {
                    new NotificationClient();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        System.out.println("Starting client...");

        // Server RMI
        Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
        ServerFacade serverFacade = (ServerFacade) registry.lookup(ServerFacade.class.getSimpleName()); 
        System.out.println("found server facade: " + serverFacade);

        // get some facades
        UserFacade userFacade = serverFacade.getUserFacade();
        MessageFacade messageFacade = serverFacade.getMessageFacade();
        ChannelFacade channelFacade = serverFacade.getChannelFacade();

        // test facades
        messageFacade.sendMessage("KAKAKAIAKAIAKAIAKAIAKAIAK", 2, 2);
        System.out.println(userFacade.getAllUsers());
        System.out.println(messageFacade.getAllMessagesByChannelId(2));
        System.out.println(channelFacade.getAllChannelsByUserId(1));

    }

}
