package com.odfin.voicechat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class VoiceDataPacket implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended to maintain serialization compatibility

    private byte[] data;
    private int channelId;
    private int userId;

    public VoiceDataPacket(byte[] data, int channelId, int userId) {
        this.data = data;
        this.channelId = channelId;
        this.userId = userId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getChannelId() {
        return channelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Serializes the fields
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Deserializes the fields
    }
}
