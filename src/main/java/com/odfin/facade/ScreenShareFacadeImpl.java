package com.odfin.facade;

import com.odfin.persistence.domain.User;
import com.odfin.screenshare.ScreenClientHandler;
import com.odfin.screenshare.ScreenServer;
import com.odfin.voicechat.VoiceClientHandler;
import com.odfin.voicechat.VoiceServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ScreenShareFacadeImpl extends UnicastRemoteObject implements ScreenShareFacade{

    protected ScreenShareFacadeImpl() throws RemoteException {
        super(65300);
    }

    @Override
    public List<User> getScreenSharingUsersByChannelId(int channelId) throws RemoteException {
        List<Integer> userIds = new ArrayList<>();
        for(ScreenClientHandler s : ScreenServer.clientHandlers){
            if(channelId == s.channelId && s.sending) {
                userIds.add(s.userId);
            }
        }

        List<User> users = new ArrayList<>();
        for(int userId : userIds){
            users.add(new ServerFacadeImpl().getUserFacade().getUserById(userId));
        }

        return users;
    }

    @Override
    public boolean isUserStreamingInChannel(int userId, int channelId) throws RemoteException {
        for(ScreenClientHandler s : ScreenServer.clientHandlers){
            System.out.println(s.channelId);
            System.out.println(s.userId);
            System.out.println(s.sending);
            if(s.channelId == channelId && s.userId == userId && s.sending){
                return true;
            }
        }

        return false;
    }

}
