package com.odfin.persistence.dao;

import com.odfin.persistence.domain.ChannelGroup;

import java.sql.SQLException;
import java.util.List;

public interface ChannelGroupDAO {

    ChannelGroup getServerById(int serverId) throws SQLException;
    List<ChannelGroup> getServersByUserID(int userID) throws SQLException;
    void updateServer(ChannelGroup channelGroup)throws SQLException;
    void insertServer(ChannelGroup channelGroup)throws SQLException;
    boolean deleteServer(int serverId)throws SQLException;

}
