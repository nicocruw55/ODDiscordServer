package com.odfin.core.Notification;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationServer {
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
            notifyClients("Test notify");
        }
    }

    public static void notifyClients(String message) {
        synchronized (handlers) {
            for (NotificationClientHandler handler : handlers) {
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
