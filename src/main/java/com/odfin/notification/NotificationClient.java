package com.odfin.notification;

import com.odfin.persistence.util.ServerHelper;

import java.io.*;
import java.net.*;

public class NotificationClient extends Thread{

    private static final String SERVER_ADDRESS = ServerHelper.SERVER_NAME;
    private static final int SERVER_PORT = 5000;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int userId;

    public NotificationClient(int userId) throws IOException {
        this.socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.userId = userId;
    }

    @Override
    public void run(){
        out.println(userId);  // send userId first before starting loop

        while (true) {
            try {
                String message;
                if ((message = in.readLine()) != null)
                    System.out.println("Notification: " + message);
            } catch (IOException e) {
                break;
            }
        }
    }
}
