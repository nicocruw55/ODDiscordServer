package com.odfin.voicechat;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class VoiceChatServer {
    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 4096;
    private static final int SAMPLE_RATE = 16000;
    private static final int SAMPLE_SIZE_IN_BITS = 16;
    private static final int CHANNELS = 1; // Mono

    public static void main(String[] args) {
        AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, true, true);
        DataLine.Info infoForPlayback = new DataLine.Info(SourceDataLine.class, format);

        try (
                SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(infoForPlayback);
                DatagramSocket receiveSocket = new DatagramSocket(PORT)
        ) {
            speakers.open(format);
            speakers.start();

            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Voice Chat Server is running...");

            Thread receiveThread = new Thread(() -> {
                while (true) {
                    try {
                        receiveSocket.receive(packet);
                        speakers.write(packet.getData(), 0, packet.getLength());
                    } catch (IOException e) {
                        System.err.println("IOException while receiving data: " + e.getMessage());
                        break; // Stop the thread if there is an exception
                    }
                }
            });

            receiveThread.start();

            // Keep the main thread alive to keep the server running
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                receiveSocket.close();
                speakers.stop();
                speakers.close();
                System.out.println("Voice Chat Server stopped.");
            }));

            // Keep the main thread alive to keep the server running
            synchronized (VoiceChatServer.class) {
                try {
                    VoiceChatServer.class.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        } catch (LineUnavailableException | SocketException e) {
            e.printStackTrace();
        }
    }
}
