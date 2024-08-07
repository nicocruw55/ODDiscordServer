package com.odfin.persistence.dao;

import com.odfin.persistence.domain.ChannelGroup;

import java.sql.SQLException;
import java.util.List;

public interface ChannelGroupDAO {

    ChannelGroup getChannelGroupById(int channelGroupId) throws SQLException;
    List<ChannelGroup> getChannelGroupsByUserID(int userID) throws SQLException;
    void updateChannelGroup(ChannelGroup channelGroup)throws SQLException;
    ChannelGroup createChannelGroup(ChannelGroup channelGroup, int userID)throws SQLException;
    boolean deleteChannelGroup(int channelGroupId)throws SQLException;

}
