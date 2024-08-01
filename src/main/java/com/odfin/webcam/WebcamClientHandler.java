package com.odfin.webcam;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class WebcamClientHandler implements Runnable {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ArrayList<WebcamClientHandler> clientHandlers;

    public WebcamClientHandler(Socket clientSocket, ArrayList<WebcamClientHandler> clientHandlers) throws IOException {
        this.clientSocket = clientSocket;
        this.clientHandlers = clientHandlers;
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] data = (byte[]) in.readObject();
                broadcast(data);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client getrennt: " + clientSocket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientHandlers.remove(this);
        }
    }

    private void broadcast(byte[] data) {
        for (WebcamClientHandler handler : clientHandlers) {
            try {
                handler.out.writeObject(data);
                handler.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}