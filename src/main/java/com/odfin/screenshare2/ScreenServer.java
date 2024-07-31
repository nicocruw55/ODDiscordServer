package com.odfin.screenshare2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ScreenServer {

    public static List<ScreenClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(500);
        while (true) {
            System.out.println("Waiting");
            Socket s = ss.accept();
            System.out.println("Connection");
            ScreenClientHandler handler = new ScreenClientHandler(s);
            System.out.println("xcxc");
            clientHandlers.add(handler);
        }
    }
}
