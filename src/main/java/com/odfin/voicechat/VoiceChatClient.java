package com.odfin.voicechat;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.*;

public class VoiceChatClient {
    private static final int SAMPLE_RATE = 16000;
    private static final int BUFFER_SIZE = 4096;
    private static final int PORT = 6000;
    private static final String SERVER_ADDRESS = "localhost"; // Ersetze dies durch die Server-IP-Adresse oder Domain
    private static final int HEARTBEAT_INTERVAL = 5000; // Heartbeat-Intervall in Millisekunden

    public static void main(String[] args) {
        AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
        DataLine.Info infoForRecording = new DataLine.Info(TargetDataLine.class, format);

        try (
                TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(infoForRecording);
                DatagramSocket sendSocket = new DatagramSocket()
        ) {
            microphone.open(format);
            microphone.start();

            byte[] buffer = new byte[BUFFER_SIZE];
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);

            System.out.println("Voice Chat Client is running...");

            Thread sendThread = new Thread(() -> {
                while (true) {
                    try {
                        // Sende Sprachdaten
                        int bytesRead = microphone.read(buffer, 0, buffer.length);
                        DatagramPacket packet = new DatagramPacket(buffer, bytesRead, serverAddress, PORT);
                        sendSocket.send(packet);

                        // Sende Heartbeat-Nachricht
                        DatagramPacket heartbeatPacket = new DatagramPacket("HEARTBEAT".getBytes(), "HEARTBEAT".length(), serverAddress, PORT);
                        sendSocket.send(heartbeatPacket);

                        // Warte das nächste Intervall
                        Thread.sleep(HEARTBEAT_INTERVAL);
                    } catch (IOException | InterruptedException e) {
                        System.err.println("IOException while sending data: " + e.getMessage());
                        break; // Stop the thread if there is an exception
                    }
                }
            });

            sendThread.start();

            // Keep the main thread alive to keep the client running
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                microphone.stop();
                microphone.close();
                sendSocket.close();
                System.out.println("Voice Chat Client stopped.");
            }));

            // Keep the main thread alive to keep the client running
            synchronized (VoiceChatClient.class) {
                try {
                    VoiceChatClient.class.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        } catch (LineUnavailableException | SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
