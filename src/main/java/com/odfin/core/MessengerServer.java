package com.odfin.core;

import com.odfin.facade.VoiceChatFacadeImpl;
import com.odfin.notification.NotificationServer;
import com.odfin.facade.ServerFacade;
import com.odfin.facade.ServerFacadeImpl;
import com.odfin.util.ServerHelper;
import com.odfin.voicechat.VoiceServer;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MessengerServer {

    static VoiceServer voiceServer = new VoiceServer();
    static Thread voiceServerThread = new Thread(voiceServer);

    public static void main(String[] args) throws IOException, InterruptedException {
        new NotificationServer().start();
        voiceServerThread.start();

        System.setProperty("java.rmi.server.hostname", ServerHelper.SERVER_NAME);
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        ServerFacadeImpl serverFacadeImpl = new ServerFacadeImpl();
        registry.rebind(ServerFacade.class.getSimpleName(), serverFacadeImpl);
        System.out.println("Messenger RMI registry created...");

        while(true){
            Thread.sleep(1000);
            System.out.println("VoiceServer Members:" + VoiceServer.clientHandlers.size());
        }
    }

}
