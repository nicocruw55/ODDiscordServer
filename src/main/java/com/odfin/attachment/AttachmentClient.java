package com.odfin.attachment;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AttachmentClient {

    public static void main(String[] args) {
        String uploadUrl = "http://localhost:8000/upload";
        String filePath = "C:\\Users\\a.asfour\\IdeaProjects\\ODDiscordServer\\emoji.png"; // Pfad zum Bild auf dem Client

        try {
            String response = uploadImage(uploadUrl, filePath);
            System.out.println("Upload Response: " + response);

            // Nachdem das Bild hochgeladen wurde, rufe es vom Server ab
            String imageUrl = "http://localhost:8000/uploads/C:\\Users\\a.asfour\\IdeaProjects\\ODDiscordServer\\emoji.png"; // Angepasste URL
            BufferedImage image = loadImageFromUrl(imageUrl);
            if (image != null) {
                displayImage(image);
            } else {
                System.out.println("Bild konnte nicht geladen werden.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private static String uploadImage(String uploadUrl, String filePath) throws IOException {
        File imageFile = new File(filePath);
        HttpURLConnection connection = (HttpURLConnection) new URL(uploadUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=*****");

        try (OutputStream outputStream = connection.getOutputStream();
             FileInputStream fileInputStream = new FileInputStream(imageFile)) {

            outputStream.write(("--*****\r\n").getBytes());
            outputStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + imageFile.getName() + "\"\r\n").getBytes());
            outputStream.write(("Content-Type: image/jpeg\r\n\r\n").getBytes());

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.write(("\r\n--*****--\r\n").getBytes());
            outputStream.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        } else {
            throw new IOException("Server antwortete mit Code: " + responseCode);
        }
    }

    private static BufferedImage loadImageFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Server antwortete mit Code: " + responseCode);
        }

        try (InputStream inputStream = connection.getInputStream()) {
            return ImageIO.read(inputStream);
        } finally {
            connection.disconnect();
        }
    }

    private static void displayImage(BufferedImage image) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());

        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }
}
