package com.odfin.voicechat;

import java.io.Serializable;

public class VoiceChannelDataPacket implements Serializable {
    private byte[] data;
    private String vc;

    public VoiceChannelDataPacket(byte[] data, String vc) {
        this.data = data;
        this.vc = vc;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getVc() {
        return vc;
    }

    public void setVc(String vc) {
        this.vc = vc;
    }
}
