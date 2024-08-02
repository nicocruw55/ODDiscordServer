package com.odfin.notification;

import java.io.*;
import java.net.*;

public class NotificationClientHandler {

    public Socket socket;
    public PrintWriter out;
    public BufferedReader in;
    public int userId;

    public NotificationClientHandler(Socket socket) {
        this.socket = socket;

        try {
            out = new PrintWriter(this.socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String userIdString = in.readLine();
            this.userId = Integer.parseInt(userIdString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
