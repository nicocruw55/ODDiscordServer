package com.odfin.core.Notification;

import java.io.*;
import java.net.*;

public class NotificationClient {
    private static final String SERVER_ADDRESS = "cruw-community.de";
    private static final int SERVER_PORT = 5000;

    public NotificationClient(int userId) throws IOException {
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // send userId first before starting loop
        out.println(userId);

        String message;
        while ((message = in.readLine()) != null) {
            System.out.println("Server-Benachrichtigung: " + message);
        }
    }
}
