package com.odfin.webcam;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WebcamServer {
    private ServerSocket serverSocket;
    public ArrayList<WebcamClientHandler> clientHandlers = new ArrayList<>();

    public WebcamServer(int port) throws Exception {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() {
        System.out.println("Server gestartet auf Port: " + serverSocket.getLocalPort());
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client verbunden: " + clientSocket.getInetAddress());
                    WebcamClientHandler handler = new WebcamClientHandler(clientSocket, clientHandlers);
                    clientHandlers.add(handler);
                    new Thread(handler).start();
                } catch (RuntimeException | IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        try {
            WebcamServer server = new WebcamServer(6000);
            server.start();
            System.out.println("Webcam Server wurde erfolgreich auf Port 6000 gestartet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}