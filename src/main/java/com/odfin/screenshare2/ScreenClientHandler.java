package com.odfin.screenshare2;

import java.io.*;

public class ScreenClientHandler {
    public ObjectOutputStream output;
    private ObjectInputStream in;
    public String voiceChatID = "";

    public ScreenClientHandler(java.net.Socket socket) throws Exception {
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        new Thread(() -> {
            while (true) {
                try{
                    byte[] d = (byte[]) in.readObject();
                    for(ScreenClientHandler s : ScreenServer.clientHandlers){
                        //if(v == this) continue;
                        //if(v.voiceChatID.equals(voiceChatID)){
                        s.output.writeObject(d);
                        s.output.flush();
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
                    ScreenServer.clientHandlers.remove(this);
                    System.out.println("Removing");
                    break;
                }
            }
        }).start();
    }
}
