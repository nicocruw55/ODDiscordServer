package com.odfin.persistence.dao;
import com.odfin.persistence.domain.Channel;

import java.sql.SQLException;
import java.util.List;

public interface ChannelDAO {

    Channel getChannelById(int chatId) throws SQLException;
    List<Channel> getChannelsByChannelGroupID(int channelGroupId) throws SQLException;
    List<Channel> getAllChannels() throws SQLException;
    List<Channel> getAllChannelsByUserId(int userId) throws SQLException;
    void updateChannel(Channel channel) throws SQLException;
    void insertChannel(Channel channel) throws SQLException;
    void deleteChannel(int chatId) throws SQLException;

}
