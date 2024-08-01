package com.odfin.screenshare2;

import com.odfin.persistence.util.ServerHelper;
import com.odfin.voicechat.VoiceDataPacket;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class ScreenClient {

    private static final String SERVER_ADDRESS = ServerHelper.SERVER_NAME;
    private static final int PORT = 500;

    public static void main(String[] args) throws IOException, AWTException {
        JFrame frame = new JFrame("Screen Share Client");
        JLabel label = new JLabel();
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        Socket socket = new Socket(SERVER_ADDRESS, PORT);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        Robot robot = new Robot();

        // Sending
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    BufferedImage screenCapture = robot.createScreenCapture(screenRect);
                    ImageIO.write(screenCapture, "jpeg", baos);
                    oos.writeObject(baos.toByteArray());
                    oos.reset();
                    baos.reset();
                    Thread.sleep(1000/60);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();

        // Receiving
        while (true) {
            try {
                byte[] imageBytes = (byte[]) ois.readObject();
                ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                BufferedImage image = ImageIO.read(bais);

                Image scaledImage = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
                frame.repaint();
            } catch (Exception e) {
                System.err.println("Error processing image: " + e.getMessage());
            }
        }

    }
}
