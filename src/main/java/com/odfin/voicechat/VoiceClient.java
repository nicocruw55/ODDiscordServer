package com.odfin.voicechat;

import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class VoiceClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 55;
    private static String voiceChat = "uid";

    public static void main(String[] args) throws IOException, LineUnavailableException {
        Socket socket;
        if(args.length > 0){
            socket = new Socket(args[0],SERVER_PORT);
        } else {
            socket = new Socket(SERVER_ADDRESS,SERVER_PORT);
        }

        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        System.out.println("Available mixers:");
        for (int i = 0; i < mixers.length; i++) {
            System.out.println(i + ": " + mixers[i].getName());
        }

        // Select a mixer for the microphone (this could be done through user input or a predefined index)
        int selectedMicrophoneIndex = 12; // Change this to the index of the desired microphone mixer
        Mixer microphoneMixer = AudioSystem.getMixer(mixers[selectedMicrophoneIndex]);

        // Select a mixer for the speakers (this could be done through user input or a predefined index)
        int selectedSpeakerIndex = 7; // Change this to the index of the desired speaker mixer
        Mixer speakerMixer = AudioSystem.getMixer(mixers[selectedSpeakerIndex]);

        // Define audio format
        AudioFormat format = new AudioFormat(44100, 16, 1, true, true);

        // Get and open the TargetDataLine for the selected microphone mixer
        DataLine.Info microphoneInfo = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine microphone = (TargetDataLine) microphoneMixer.getLine(microphoneInfo);
        microphone.open(format);
        microphone.start();

        // Get and open the SourceDataLine for the selected speaker mixer
        DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine speakers = (SourceDataLine) speakerMixer.getLine(speakerInfo);
        speakers.open(format);
        speakers.start();

        System.out.println("Everything setup correctly");

        Thread t = new Thread(() -> {
            while (true) {
                try {
                    VoiceChannelDataPacket data = (VoiceChannelDataPacket) input.readObject();
                    speakers.write(data.getData(), 0,data.getData().length);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();


        while (true) {
            byte[] buffer = new byte[1024];
            microphone.read(buffer,0,buffer.length);
            VoiceChannelDataPacket data = new VoiceChannelDataPacket(buffer,voiceChat);
            output.writeObject(data);
            output.flush();
        }
    }
}

