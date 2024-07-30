package org.example;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class VoiceChatServer {
    private static final int PORT = 6000;
    private static final int BUFFER_SIZE = 4096;
    private static final int SAMPLE_RATE = 16000;
    private static final long HEARTBEAT_TIMEOUT = 10000; // Timeout für Heartbeat in Millisekunden

    private static Map<InetAddress, Long> clientLastHeartbeat = new HashMap<>();

    public static void main(String[] args) {
        AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try (
                SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);
                DatagramSocket socket = new DatagramSocket(PORT)
        ) {
            speakers.open(format);
            speakers.start();

            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Voice Chat Server is running...");

            Thread heartbeatChecker = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(HEARTBEAT_TIMEOUT);
                        long now = System.currentTimeMillis();
                        clientLastHeartbeat.entrySet().removeIf(entry -> now - entry.getValue() > HEARTBEAT_TIMEOUT);
                        System.out.println("Active clients: " + clientLastHeartbeat.keySet());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            heartbeatChecker.start();

            while (true) {
                try {
                    socket.receive(packet);
                    InetAddress clientAddress = packet.getAddress();
                    String message = new String(packet.getData(), 0, packet.getLength());

                    if ("HEARTBEAT".equals(message)) {
                        clientLastHeartbeat.put(clientAddress, System.currentTimeMillis());
                    } else {
                        speakers.write(packet.getData(), 0, packet.getLength());
                    }
                } catch (IOException e) {
                    System.err.println("IOException while receiving data: " + e.getMessage());
                }
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
