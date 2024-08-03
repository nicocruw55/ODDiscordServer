package com.odfin.facade;

import com.odfin.voicechat.VoiceClientHandler;
import com.odfin.voicechat.VoiceServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoiceChatFacadeImpl extends UnicastRemoteObject implements VoiceChatFacade{
    protected VoiceChatFacadeImpl() throws RemoteException {
        super(65300);
    }

    @Override
    public int[] getVoiceUsersByChannelId(int channelId) throws RemoteException {
        List<Integer> userIds = new ArrayList<>();
        for(VoiceClientHandler v : VoiceServer.clientHandlers){
            if(channelId == v.channelId) {
                userIds.add(v.userId);
            }
        }

        // convert to array
        int[] userIdsArray = new int[userIds.size()];
        for(int i = 0; i<userIds.size(); i++){
            userIdsArray[i] = userIds.get(i);
        }

        return userIdsArray;
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
