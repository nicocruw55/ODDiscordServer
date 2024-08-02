package com.odfin.facade;

import com.odfin.voicechat.VoiceServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VoiceChatFacadeImpl extends UnicastRemoteObject implements VoiceChatFacade{
    protected VoiceChatFacadeImpl() throws RemoteException {
        super(65300);
    }

    @Override
    public int getClientAmount() {
        return VoiceServer.clientHandlers.size();
    }
}
