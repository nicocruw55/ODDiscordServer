package com.odfin.voicechat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.odfin.persistence.util.ServerHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VoiceServer {

    public static List<VoiceClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        //Springboot
        SpringApplication app = new SpringApplication(VoiceServer.class);
        //app.setDefaultProperties(java.util.Collections.singletonMap("server.port", "9090"));
        app.setDefaultProperties(java.util.Collections.singletonMap("server.address", ServerHelper.SERVER_NAME));
        app.run(args);
        ApplicationContext context = app.run(args);
        VoiceChatWebSocketHandler webSocketHandler = context.getBean(VoiceChatWebSocketHandler.class);


        ServerSocket ss = new ServerSocket(55);
        while (true) {
            Socket s = ss.accept();
            System.out.println("Connection");
            VoiceClientHandler handler = new VoiceClientHandler(s, webSocketHandler);
            clientHandlers.add(handler);
        }
    }
}