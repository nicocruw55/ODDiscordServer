package com.odfin.voicechat;

import java.io.*;
import java.net.Socket;

public class VoiceClientHandler {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private int voiceChatID = -1;

    public VoiceClientHandler(Socket socket) throws IOException {
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());

        new Thread(this::handleClient).start();
    }

    private void handleClient() {
        try {
            while (true) {
                VoiceDataPacket dataPacket = (VoiceDataPacket) input.readObject();
                voiceChatID = dataPacket.getVc();

                for (VoiceClientHandler clientHandler : VoiceServer.clientHandlers) {
                    if (shouldSendPacketToClient(clientHandler)) {
                        sendPacketToClient(clientHandler, dataPacket);
                    }
                }
            }
        } catch (Exception e) {
            handleDisconnection();
        }
    }

    private boolean shouldSendPacketToClient(VoiceClientHandler clientHandler) {
        return clientHandler != this && clientHandler.voiceChatID == this.voiceChatID;
    }

    private void sendPacketToClient(VoiceClientHandler clientHandler, VoiceDataPacket dataPacket) throws IOException {
        clientHandler.output.writeObject(dataPacket);
        clientHandler.output.flush();
    }

    private void handleDisconnection() {
        try {
            System.out.println("Disconnect from vcs");
            VoiceServer.clientHandlers.remove(this);
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
