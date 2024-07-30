package com.odfin.screenshare;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ScreenShareClient {
    private static final String SERVER_ADDRESS = "localhost"; // Change to your server's IP
    private static final int PORT = 5000;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Screen Share Client");
        JLabel label = new JLabel();
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            while (true) {
                byte[] imageBytes = (byte[]) ois.readObject();
                ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                BufferedImage image = ImageIO.read(bais);
                label.setIcon(new ImageIcon(image));
                frame.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
