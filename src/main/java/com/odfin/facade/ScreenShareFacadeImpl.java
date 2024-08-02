package com.odfin.facade;

import com.odfin.screenshare.ScreenClientHandler;
import com.odfin.screenshare.ScreenServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ScreenShareFacadeImpl extends UnicastRemoteObject implements ScreenShareFacade{

    protected ScreenShareFacadeImpl() throws RemoteException {
        super(65300);
    }

    @Override
    public int[] getScreenSharingUsersByChannelId(int channelId) throws RemoteException {
        List<Integer> userIds = new ArrayList<>();
        for(ScreenClientHandler s : ScreenServer.clientHandlers){
            if(channelId == s.channelId && s.sending) {
                userIds.add(s.userId);
            }
        }

        // convert to array
        int[] userIdsArray = new int[userIds.size()];
        for(int i = 0; i<userIds.size(); i++){
            userIdsArray[i] = userIds.get(i);
        }

        return userIdsArray;
    }

}
