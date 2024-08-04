package com.odfin.facade;

import com.odfin.persistence.domain.User;
import com.odfin.voicechat.VoiceClientHandler;
import com.odfin.voicechat.VoiceServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class VoiceChatFacadeImpl extends UnicastRemoteObject implements VoiceChatFacade{
    protected VoiceChatFacadeImpl() throws RemoteException {
        super(65300);
    }

    @Override
    public List<User> getVoiceUsersByChannelId(int channelId) throws RemoteException {
        List<Integer> userIds = new ArrayList<>();
        for(VoiceClientHandler v : VoiceServer.clientHandlers){
            if(channelId == v.channelId) {
                userIds.add(v.userId);
            }
        }
        
        List<User> users = new ArrayList<>();
        for(int userId : userIds)
            users.add(new ServerFacadeImpl().getUserFacade().getUserById(userId));

        return users;
    }

    @Override
    public boolean isUserInVoiceChannel(int userId, int channelId) throws RemoteException {
        for(VoiceClientHandler v : VoiceServer.clientHandlers){
            if(v.channelId == channelId && v.userId == userId){
                return true;
            }
        }

        return false;
    }
}
