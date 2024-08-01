package com.odfin.voicechat;

import java.io.Serializable;

public class VoiceDataPacket implements Serializable {
    private byte[] data;
    private int vc;
    private int user;

    public VoiceDataPacket(byte[] data, int vc, int user) {
        this.data = data;
        this.vc = vc;
        this.user = user;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getVc() {
        return vc;
    }

    public void setVc(int vc) {
        this.vc = vc;
    }
}
