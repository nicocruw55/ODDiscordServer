package com.odfin.voicechat;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.Socket;

public class VoiceClientHandler {
    public ObjectOutputStream output;
    private ObjectInputStream in;
    public int voiceChatID = -1;
    public int userId = -1;

    @Autowired
    private VoiceChatWebSocketHandler webSocketHandler;

    public VoiceClientHandler(Socket socket, VoiceChatWebSocketHandler webSocketHandler) throws Exception {
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.webSocketHandler = webSocketHandler;

        new Thread(() -> {
            // wait for initial data to set voicechat and userid
            try {
                VoiceDataPacket d = (VoiceDataPacket) in.readObject();
                voiceChatID = d.getVc();
                userId = d.getUser();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                webSocketHandler.sendMessageToAll(String.format("JOIN,%d,%d", userId, voiceChatID));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while (true) {
                try{
                    VoiceDataPacket d = (VoiceDataPacket) in.readObject();
                    voiceChatID = d.getVc();
                    userId = d.getUser();

                    for(VoiceClientHandler v : VoiceServer.clientHandlers){
                        //  continue if own client
                        if(v == this)
                            continue;

                        // only send to clients in the same voicechat
                        if(v.voiceChatID == (voiceChatID)){
                            v.output.writeObject(d);
                            v.output.flush();
                        }
                    }
                }
                catch (Exception e){
                    try {
                        System.out.println("Disconnect from vcs");
                        VoiceServer.clientHandlers.remove(this);
                        webSocketHandler.sendMessageToAll(String.format("LEAVE,%d,%d", userId, voiceChatID));
                        output.close();
                        in.close();
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
            }
        }).start();
    }
}
