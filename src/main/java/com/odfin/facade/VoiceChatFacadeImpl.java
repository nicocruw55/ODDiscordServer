package com.odfin.facade;

import com.odfin.voicechat.VoiceClientHandler;
import com.odfin.voicechat.VoiceServer;

import java.util.ArrayList;
import java.util.List;

public class VoiceChatFacadeImpl implements VoiceChatFacade{

    @Override
    public List<Integer> getAllCallersFromChannelId(int channelId) {
        List<Integer> callerIds = new ArrayList<>();
        List<VoiceClientHandler> clientHandlers = VoiceServer.clientHandlers;

        for(VoiceClientHandler v : clientHandlers)
            if(v.voiceChatID == channelId)
                callerIds.add(v.userId);

        return callerIds;
    }

}
