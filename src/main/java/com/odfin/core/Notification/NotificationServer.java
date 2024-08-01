package com.odfin.core.Notification;

import com.odfin.facade.ServerFacadeImpl;
import com.odfin.persistence.domain.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationServer{
    private static final int PORT = 5000;
    public static List<NotificationClientHandler> handlers = Collections.synchronizedList(new ArrayList<>());

    public NotificationServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("notification server startet on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            NotificationClientHandler handler = new NotificationClientHandler(clientSocket);
            handlers.add(handler);
            handler.start();
            System.out.println("connection");
            notifyClients("Test notify", 1);
        }
    }

    public static void notifyClients(String message, int channelId) {
        List<User> usersFromChannel;
        try {
            usersFromChannel = new ServerFacadeImpl().getUserFacade().getAllUsersFromChannel(channelId);
        } catch (RemoteException e) {
            throw new RuntimeException("Fehler beim Abrufen der Benutzer aus dem Channel", e);
        }

        // get all user ids from user in channel
        List<Integer> userIds = new ArrayList<>();
        for (User user : usersFromChannel) {
            userIds.add(user.getId());
        }

        synchronized (handlers) {
            for (NotificationClientHandler handler : handlers) {
                if (userIds.contains(handler.userId)) {
                    try {
                        PrintWriter out = new PrintWriter(handler.clientSocket.getOutputStream(), true);
                        out.println(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
