package com.odfin.webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class WebcamClientFrame {
    private static final String SERVER_ADDRESS = "cruw-community.de";
    private static final int SERVER_PORT = 6000;

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Webcam Client");
        frame.setLayout(new GridLayout(1, 2));
        JLabel ownLabel = new JLabel();
        JLabel partnerLabel = new JLabel();
        frame.add(ownLabel);
        frame.add(partnerLabel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        WebcamClient client = new WebcamClient(SERVER_ADDRESS, SERVER_PORT);

        new Thread(() -> receiveAndDisplay(client, ownLabel, partnerLabel)).start();
    }

    private static void receiveAndDisplay(WebcamClient client, JLabel ownLabel, JLabel partnerLabel) {
        try {
            while (true) {
                byte[] data = client.receiveWebcamData();
                if (data != null) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(data);
                    BufferedImage image = ImageIO.read(bais);
                    Image scaledImage = image.getScaledInstance(ownLabel.getWidth(), ownLabel.getHeight(), Image.SCALE_SMOOTH);
                    ownLabel.setIcon(new ImageIcon(scaledImage));
                    partnerLabel.setIcon(new ImageIcon(scaledImage));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}