package com.odfin.core.Notification;

import java.io.*;
import java.net.*;

public class NotificationClientHandler {
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


}
