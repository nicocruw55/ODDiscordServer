package com.odfin.core.Notification;

import java.io.*;
import java.net.*;

public class NotificationClientHandler extends Thread {
    public Socket clientSocket;
    public PrintWriter out;
    public BufferedReader in;
    public int userId;

    public NotificationClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String userIdString = in.readLine();
            this.userId = Integer.parseInt(userIdString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                System.out.println("Nachricht vom Client: " + message);
            }
        } catch (IOException e) {
            try {
                out.close();
                in.close();
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            NotificationServer.handlers.remove(this);
        }
    }

}
