package com.odfin.facade;

import com.odfin.voicechat.VoiceServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerFacadeImpl extends UnicastRemoteObject implements ServerFacade{

    private static MessageFacade messageFacade;
    private static UserFacade userFacade;
    private static ChannelFacade channelFacade;
    private static VoiceChatFacade voiceChatFacade;

    public ServerFacadeImpl() throws RemoteException {
        super(65300);
    }

    @Override
    public MessageFacade getMessageFacade() throws RemoteException {
        if(messageFacade == null)
            messageFacade = new MessageFacadeImpl();

        return messageFacade;
    }

    @Override
    public UserFacade getUserFacade() throws RemoteException {
        if(userFacade == null)
            userFacade = new UserFacadeImpl();

        return userFacade;
    }

    @Override
    public ChannelFacade getChannelFacade() throws RemoteException {
        if(channelFacade == null)
            channelFacade = new ChannelFacadeImpl();

        return channelFacade;
    }

    @Override
    public VoiceChatFacade getVoiceChatFacade() throws RemoteException {
        if(voiceChatFacade == null)
            voiceChatFacade = new VoiceChatFacadeImpl();

        return voiceChatFacade;
    }

}
