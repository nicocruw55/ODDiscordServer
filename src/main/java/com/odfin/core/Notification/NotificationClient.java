package com.odfin.core.Notification;

import java.io.*;
import java.net.*;

public class NotificationClient {
    private static final String SERVER_ADDRESS = "localhost"; // Ersetze dies durch die tatsächliche Server-IP
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            System.out.println("Verbunden mit dem Server");

            // Warte auf Nachrichten vom Server
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Server-Benachrichtigung: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
