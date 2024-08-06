package com.odfin.screenshare;

import com.odfin.notification.NotificationServer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ScreenClientHandler {

    public ObjectOutputStream output;
    public int channelId = -1;
    public int userId = -1;
    public int watchingId = -1;
    public boolean sending = false;
    private ObjectInputStream in;
    private Socket socket;

    public ScreenClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        new Thread(() -> {
            try {
                // first message
                ScreenDataPacket d = (ScreenDataPacket) in.readObject();
                channelId = d.getChannelId();
                userId = d.getUserId();
                watchingId = d.getWatchingId();
                sending = d.isSending();
                NotificationServer.notifyClients("Stream," + channelId);

                // loop -> gets input from corresponding client and sends it to all other clients
                while (true) {
                    d = (ScreenDataPacket) in.readObject();
                    for (ScreenClientHandler s : ScreenServer.clientHandlers) {
                        if (s == this) continue;
                        s.output.writeObject(d);
                        s.output.flush();
                    }
                }
            } catch (Exception e) {
                cleanup();
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
        ScreenServer.clientHandlers.remove(this);
        NotificationServer.notifyClients("Stream," + channelId);
        System.out.println("Removing");
    }
}
