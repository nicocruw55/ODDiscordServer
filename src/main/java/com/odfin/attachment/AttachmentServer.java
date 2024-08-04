package com.odfin.attachment;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class AttachmentServer {

    private static final int PORT = 8000;
    private static final String UPLOAD_DIR = "uploads";

    public static void main(String[] args) throws Exception {
        // Erstelle das Upload-Verzeichnis, wenn es nicht existiert
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Erstelle und starte den HTTP-Server
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/upload", new UploadHandler());
        server.setExecutor(null); // Standard-Executor verwenden
        server.start();
        System.out.println("Server gestartet auf Port " + PORT);
    }

    static class UploadHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Empfange die Datei
                InputStream inputStream = exchange.getRequestBody();
                String fileName = UUID.randomUUID().toString() + ".jpg"; // Generiere einen zufälligen Dateinamen
                Path filePath = Paths.get(UPLOAD_DIR, fileName);

                // Speichere die Datei
                try (OutputStream outputStream = Files.newOutputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                // Antworte mit dem Pfad zur gespeicherten Datei
                String response = "Bild erfolgreich hochgeladen. Datei: " + fileName;
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                // Nur POST-Anfragen unterstützen
                String response = "Nur POST-Anfragen werden unterstützt.";
                exchange.sendResponseHeaders(405, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }
}
