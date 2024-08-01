package com.odfin.voicechat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoiceServer {

    public static List<VoiceClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        SpringApplication.run(VoiceServer.class, args);
        ServerSocket ss = new ServerSocket(55);
        while (true) {
            Socket s = ss.accept();
            System.out.println("Connection");
            VoiceClientHandler handler = new VoiceClientHandler(s);
            clientHandlers.add(handler);
        }
    }
}