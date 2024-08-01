package com.odfin.webcam;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WebcamClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public WebcamClient(String serverAddress, int serverPort) throws Exception {
        socket = new Socket(serverAddress, serverPort);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void sendWebcamData(byte[] data) throws Exception {
        out.writeObject(data);
        out.flush();
    }

    public byte[] receiveWebcamData() throws Exception {
        try {
            return (byte[]) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() throws Exception {
        out.close();
        in.close();
        socket.close();
    }
}