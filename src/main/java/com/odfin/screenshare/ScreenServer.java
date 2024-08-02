package com.odfin.screenshare;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ScreenServer implements Runnable {

    public static List<ScreenClientHandler> clientHandlers = new ArrayList<>();

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(500);
            System.out.println("screen server started on port 500...");
            while (true) {
                Socket s = ss.accept();
                System.out.println("screen client connected...");
                ScreenClientHandler handler = new ScreenClientHandler(s);
                clientHandlers.add(handler);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
