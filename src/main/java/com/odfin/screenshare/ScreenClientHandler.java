package com.odfin.screenshare;

import com.odfin.notification.NotificationServer;

import java.io.*;
import java.net.Socket;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;

public class ScreenClientHandler {

    private ObjectOutputStream output;
    private ObjectInputStream in;
    private Socket socket;
    private BufferedImage previousImage;
    public int channelId = -1;
    public int userId = -1;
    public int watchingId = -1;
    public boolean sending = false;

    public ScreenClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.previousImage = null; // Initial state, no previous image

        new Thread(() -> {
            try {
                // First message
                ScreenDataPacket d = (ScreenDataPacket) in.readObject();
                channelId = d.getChannelId();
                userId = d.getUserId();
                watchingId = d.getWatchingId();
                sending = d.isSending();
                NotificationServer.notifyClients("Stream," + channelId);

                // Main loop to handle incoming data and send updates
                while (true) {
                    d = (ScreenDataPacket) in.readObject();
                    BufferedImage currentImage = toBufferedImage(d.getData());
                    if (previousImage != null) {
                        BufferedImage diffImage = new ImageComparer().getDifferenceImage(previousImage, currentImage);

                        // Convert the difference image to byte array
                        byte[] imageData = ((DataBufferByte) diffImage.getRaster().getDataBuffer()).getData();
                        ScreenDataPacket diffPacket = new ScreenDataPacket(imageData, channelId, userId, sending, watchingId);

                        // Send the difference to all other clients
                        for (ScreenClientHandler s : ScreenServer.clientHandlers) {
                            if (s != this) {
                                s.output.writeObject(diffPacket);
                                s.output.flush();
                            }
                        }
                        previousImage = currentImage;
                    } else {
                        // For the initial connection, send the full image
                        byte[] imageData = ((DataBufferByte) currentImage.getRaster().getDataBuffer()).getData();
                        ScreenDataPacket initialPacket = new ScreenDataPacket(imageData, channelId, userId, sending, watchingId);
                        output.writeObject(initialPacket);
                        output.flush();
                        previousImage = currentImage;
                    }
                }
            } catch (Exception e) {
                cleanup();
            }
        }).start();
    }

    private BufferedImage toBufferedImage(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        return ImageIO.read(bais);
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
