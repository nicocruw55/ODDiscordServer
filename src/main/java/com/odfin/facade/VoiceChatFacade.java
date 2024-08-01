package com.odfin.facade;

import java.util.List;

public interface VoiceChatFacade {

    List<Integer> getAllCallersFromChannelId(int channelId);

}
