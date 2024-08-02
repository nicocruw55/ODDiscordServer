package com.odfin.voicechat;

import com.odfin.notification.NotificationServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class VoiceClientHandler {
    public ObjectOutputStream output;
    public int channelId = -1;
    public int userId = -1;
    private final ObjectInputStream in;
    private final Socket socket;

    public VoiceClientHandler(Socket socket) throws Exception {

        this.socket = socket;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        new Thread(() -> {
            // first message
            try {
                VoiceDataPacket d = (VoiceDataPacket) in.readObject();
                channelId = d.getChannelId();
                userId = d.getUserId();
                NotificationServer.notifyClientsToUpdateChannel(channelId);
            } catch (Exception e) {
                cleanup();
            }

            // loop -> gets input from corresponding client and sends it to all other clients
            while (true) {
                try {
                    if(socket.isClosed()){
                        System.out.println("Socket closed?");
                    }
                    VoiceDataPacket d = (VoiceDataPacket) in.readObject();
                    for (VoiceClientHandler v : VoiceServer.clientHandlers) {
                        //if(v == this) continue;
                        //if(v.voiceChatID.equals(voiceChatID)){
                        v.output.writeObject(d);
                        v.output.flush();
                        //}
                    }
                } catch (Exception e) {
                    cleanup();
                    break;
                }
            }
        }).start();

    }

    public void cleanup() {
        try {
            output.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        VoiceServer.clientHandlers.remove(this);
        NotificationServer.notifyClientsToUpdateChannel(channelId);
        System.out.println("removing voice client...");
    }
}
