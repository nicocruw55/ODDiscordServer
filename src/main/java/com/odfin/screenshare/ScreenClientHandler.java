package com.odfin.screenshare;

import java.io.*;

public class ScreenClientHandler {
    public ObjectOutputStream output;
    private ObjectInputStream in;

    public ScreenClientHandler(java.net.Socket socket) throws Exception {
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        new Thread(() -> {
            while (true) {
                try{
                    byte[] d = (byte[]) in.readObject();
                    for(ScreenClientHandler s : ScreenServer.clientHandlers){
                        if(s == this) continue;
                        s.output.writeObject(d);
                        s.output.flush();
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
