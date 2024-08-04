package com.odfin.notification;

import com.odfin.facade.ServerFacadeImpl;
import com.odfin.persistence.domain.User;
import com.odfin.util.ServerHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationServer implements Runnable {

    private static final List<NotificationClientHandler> handlers = Collections.synchronizedList(new ArrayList<>());
    private static final int PORT = ServerHelper.NOTIFICATION_SERVER_PORT;
    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(PORT);
            System.out.println("notification server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                NotificationClientHandler handler = new NotificationClientHandler(clientSocket);
                handlers.add(handler);
                System.out.println("notification client connected: " + clientSocket);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error accepting client connection", e);
        }
    }

    public static void notifyClients(String msg){
        for (NotificationClientHandler handler : handlers) {
            sendUpdateNotification(handler, msg);
        }
    }

    private static void sendUpdateNotification(NotificationClientHandler handler, String message) {
        try {
            PrintWriter out = new PrintWriter(handler.socket.getOutputStream(), true);
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
