package com.odfin.screenshare;

import com.odfin.util.ServerHelper;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ScreenServer implements Runnable {

    public static List<ScreenClientHandler> clientHandlers = new ArrayList<>();
    private static final int PORT = ServerHelper.SCREEN_SHARE_SERVER_PORT;

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(PORT);
            System.out.println("screen server started on port " + PORT);
            while (true) {
                Socket clientSocket = ss.accept();
                ScreenClientHandler handler = new ScreenClientHandler(clientSocket);
                clientHandlers.add(handler);
                System.out.println("screen client connected: " + clientSocket);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
