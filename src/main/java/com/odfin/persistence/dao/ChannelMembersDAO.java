package com.odfin.persistence.dao;

import com.odfin.persistence.domain.ChatMember;

import java.sql.SQLException;
import java.util.List;

public interface ChannelMembersDAO {
    List<ChatMember> getAllChannelIDByUserId(Integer UserId) throws SQLException; // for recent chats
}
