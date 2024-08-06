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
    private static ChannelGroupFacadeImpl channelGroupFacade;
    private static ScreenShareFacadeImpl screenShareFacade;

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

    @Override
    public ScreenShareFacade getScreenShareFacade() throws RemoteException {
        if (screenShareFacade == null)
            screenShareFacade = new ScreenShareFacadeImpl();
        return screenShareFacade;
    }


    @Override
    public ChannelGroupFacade getChannelGroupFacade() throws RemoteException {

        if(channelGroupFacade == null)
            channelGroupFacade = new ChannelGroupFacadeImpl();
        return channelGroupFacade;
    }

}
