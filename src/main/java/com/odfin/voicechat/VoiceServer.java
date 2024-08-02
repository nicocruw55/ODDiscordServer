package com.odfin.voicechat;

import com.odfin.util.ServerHelper;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class VoiceServer implements Runnable {

    public static List<VoiceClientHandler> clientHandlers = new ArrayList<>();
    private static final int PORT = ServerHelper.VOICE_SERVER_PORT;

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("voice server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                VoiceClientHandler handler = new VoiceClientHandler(clientSocket);
                clientHandlers.add(handler);
                System.out.println("voice client connected: " + clientSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
