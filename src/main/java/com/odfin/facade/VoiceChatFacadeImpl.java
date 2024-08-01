package com.odfin.facade;

import com.odfin.voicechat.VoiceClientHandler;
import com.odfin.voicechat.VoiceServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoiceChatFacadeImpl extends UnicastRemoteObject implements VoiceChatFacade{

    public VoiceChatFacadeImpl() throws RemoteException {
        super(65300);
    }

    @Override
    public int[] getAllCallersFromChannelId(int channelId) {
        System.out.println("Clientsize: " + VoiceServer.clientHandlers.size());

        List<Integer> callerIds = new ArrayList<>();
        List<VoiceClientHandler> clientHandlers = VoiceServer.clientHandlers;

        System.out.println(clientHandlers.size());
        System.out.println(VoiceServer.clientHandlers.size());

        for (VoiceClientHandler v : clientHandlers) {
            System.out.println(v.voiceChatID);
            System.out.println(channelId);
            if (v.voiceChatID == channelId) {
                callerIds.add(v.userId);
            }
        }

        // Konvertiere die Liste in ein int[]
        int[] callerIdsArray = new int[callerIds.size()];
        for (int i = 0; i < callerIds.size(); i++) {
            callerIdsArray[i] = callerIds.get(i);
        }

        System.out.println("VCF: " + Arrays.toString(callerIdsArray));
        return callerIdsArray;
    }

}
