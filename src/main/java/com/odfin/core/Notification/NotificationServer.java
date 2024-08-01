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
    private static List<Socket> clientSockets = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("notification server startet on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            clientSockets.add(clientSocket);
            System.out.println("Client connected to notification server: " + clientSocket.getInetAddress().getHostAddress());
            new NotificationClientHandler(clientSocket).start();
            notifyClients("Test notify");
        }
    }

    public static void notifyClients(String message) {
        synchronized (clientSockets) {
            for (Socket socket : clientSockets) {
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
