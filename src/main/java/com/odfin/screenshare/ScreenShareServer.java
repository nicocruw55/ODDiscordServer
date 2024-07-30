package com.odfin.screenshare;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ScreenShareServer {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Screen Share Server started...");

            // Wait for a client to connect
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Client connected...");

                Robot robot = new Robot();
                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

                while (true) {
                    BufferedImage screenCapture = robot.createScreenCapture(screenRect);
                    ImageIO.write(screenCapture, "jpeg", baos);
                    oos.writeObject(baos.toByteArray());
                    oos.reset();
                    baos.reset();

                    // Sleep for a short time to control the frame rate
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
