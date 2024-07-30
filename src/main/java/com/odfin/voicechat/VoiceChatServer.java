package com.odfin.voicechat;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class VoiceChatServer {
    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 4096;
    private static final int SAMPLE_RATE = 16000;

    public static void main(String[] args) {
        AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try (SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);
             DatagramSocket socket = new DatagramSocket(PORT)) {
            speakers.open(format);
            speakers.start();

            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                socket.receive(packet);
                speakers.write(packet.getData(), 0, packet.getLength());
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
