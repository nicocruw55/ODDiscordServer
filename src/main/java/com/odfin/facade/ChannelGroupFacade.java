package com.odfin.facade;

import com.odfin.persistence.domain.ChannelGroup;

import java.rmi.Remote;
import java.util.List;

public interface ChannelGroupFacade extends Remote {

    ChannelGroup getChannelGroupById(int chatId);
    List<ChannelGroup> getAllChannelGroups();
    List<ChannelGroup> getAllChannelGroupsByUserId(int userId);
    void updateChannelGroup(ChannelGroup channelGroup);
    void insertChannelGroup(ChannelGroup channelGroup);
    void deleteChannelGroup(int chatId);

}
