package com.odfin.screenshare;

import com.odfin.persistence.util.ServerHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ScreenShareClient {
    private static final String SERVER_ADDRESS = ServerHelper.SERVER_NAME;
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
                try {
                    byte[] imageBytes = (byte[]) ois.readObject();
                    ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                    BufferedImage image = ImageIO.read(bais);

                    // Resize image to fit the label
                    Image scaledImage = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(scaledImage));
                    frame.repaint();
                } catch (Exception e) {
                    System.err.println("Error processing image: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
