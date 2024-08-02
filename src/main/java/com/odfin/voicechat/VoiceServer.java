package com.odfin.voicechat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class VoiceServer implements Runnable{

    public static List<VoiceClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(55);
        while (true) {
            Socket s = ss.accept();
            System.out.println("Connection");
            VoiceClientHandler handler = new VoiceClientHandler(s);
            clientHandlers.add(handler);
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(55);
            System.out.println("Voice server thread started...");
            while (true) {
                Socket s = ss.accept();
                System.out.println("Connection");
                VoiceClientHandler handler = new VoiceClientHandler(s);
                clientHandlers.add(handler);
            }
        }catch(Exception e){

        }
    }
}
