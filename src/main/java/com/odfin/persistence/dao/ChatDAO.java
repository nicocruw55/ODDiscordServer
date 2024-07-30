package com.odfin.persistence.dao;
import com.odfin.persistence.domain.Chat;

import java.sql.SQLException;
import java.util.List;

public interface ChatDAO {

    Chat getChatbyID(int chatID) throws SQLException;
    List<Chat> getAllChats() throws SQLException;
    void updateChat(Chat chat) throws SQLException;
    void insertChat(Chat chat) throws SQLException;
    void deleteChat(int chatID) throws SQLException;

}
