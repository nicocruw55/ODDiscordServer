package com.odfin.voicechat;

import java.io.*;
import java.net.Socket;

public class VoiceClientHandler {
    public ObjectOutputStream output;
    private ObjectInputStream in;
    public int voiceChatID = -1;
    public int userId = -1;

    public VoiceClientHandler(Socket socket) throws Exception {
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        new Thread(() -> {
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
