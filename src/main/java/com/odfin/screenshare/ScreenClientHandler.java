package com.odfin.screenshare;

import com.odfin.notification.NotificationServer;

import java.io.*;
import java.net.Socket;

public class ScreenClientHandler {

    public ObjectOutputStream output;
    private ObjectInputStream in;
    private Socket socket;
    public int channelId = -1;
    public int userId = -1;
    public int watchingId = -1;
    public boolean sending = false;

    public ScreenClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        new Thread(() -> {
            // first message
            try {
                ScreenDataPacket d = (ScreenDataPacket) in.readObject();
                channelId = d.getChannelId();
                userId = d.getUserId();
                watchingId = d.getWatchingId();
                sending = d.isSending();
                NotificationServer.notifyClientsToUpdateChannel(channelId);
            }catch (Exception e){
                cleanup();
            }

            // loop -> gets input from corresponding client and sends it to all other clients
            while (true) {
                try{
                    ScreenDataPacket d = (ScreenDataPacket) in.readObject();
                    for(ScreenClientHandler s : ScreenServer.clientHandlers){
                        if(s == this) continue;
                        s.output.writeObject(d);
                        s.output.flush();
                    }
                }
                catch (Exception e){
                    cleanup();
                    break;
                }
            }
        }).start();
    }

    public void cleanup(){
        try {
            output.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ScreenServer.clientHandlers.remove(this);
        NotificationServer.notifyClientsToUpdateChannel(channelId);
        System.out.println("Removing");
    }
}
