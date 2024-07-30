package com.odfin.voicechat;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class VoiceChatClient {
    private static final int SAMPLE_RATE = 16000;
    private static final int BUFFER_SIZE = 4096;
    private static final int PORT = 5000;
    private static final String SERVER_ADDRESS = "172.19.115.113";

    public static void main(String[] args) {
        AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        try (TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
             DatagramSocket socket = new DatagramSocket()) {
            microphone.open(format);
            microphone.start();

            byte[] buffer = new byte[BUFFER_SIZE];
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);

            while (true) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);
                DatagramPacket packet = new DatagramPacket(buffer, bytesRead, serverAddress, PORT);
                socket.send(packet);
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
