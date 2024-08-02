package com.odfin.voicechat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class VoiceClientHandler {
    public ObjectOutputStream output;
    private ObjectInputStream in;
    public String voiceChatID = "";

    public VoiceClientHandler(java.net.Socket socket) throws Exception {
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        new Thread(() -> {
            while (true) {
                try{
                    VoiceDataPacket d = (VoiceDataPacket) in.readObject();
                    voiceChatID = d.getVc();
                    for(VoiceClientHandler v : VoiceServer.clientHandlers){
                       // if(v == this) continue;
                        //if(v.voiceChatID.equals(voiceChatID)){
                        //System.out.println("relaying");
                        v.output.writeObject(d);
                        v.output.flush();
                        //}
                    }
                }
                catch (Exception e){
                    try {
                        output.close();
                        in.close();
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    VoiceServer.clientHandlers.remove(this);
                    System.out.println("Removing");
                    break;
                }
            }
        }).start();

    }
}
