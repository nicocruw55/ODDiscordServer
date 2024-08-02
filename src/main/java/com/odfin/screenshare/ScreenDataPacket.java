package com.odfin.screenshare;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ScreenDataPacket implements Serializable {

    private static final long serialVersionUID = 1L;

    private byte[] data;
    private int channelId;
    private int userId;
    private boolean sending;
    private int watchingId;

    public ScreenDataPacket(byte[] data, int channelId, int userId, boolean sending, int watchingId) {
        this.data = data;
        this.channelId = channelId;
        this.userId = userId;
        this.sending = sending;
        this.watchingId = watchingId;
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

    public boolean isSending() {
        return sending;
    }

    public void setSending(boolean sending) {
        this.sending = sending;
    }

    public int getWatchingId() {
        return watchingId;
    }

    public void setWatchingId(int watchingId) {
        this.watchingId = watchingId;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Serializes the fields
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Deserializes the fields
    }
}
