package com.odfin.voicechat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class VoiceServer implements Runnable {

    public static List<VoiceClientHandler> clientHandlers = new ArrayList<>();

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(55);
            System.out.println("Voice server started on port 55...");
            while (true) {
                Socket s = ss.accept();
                System.out.println("voice client connected...");
                VoiceClientHandler handler = new VoiceClientHandler(s);
                clientHandlers.add(handler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
