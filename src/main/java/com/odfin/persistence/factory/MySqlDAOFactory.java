package com.odfin.persistence.factory;

import com.odfin.persistence.dao.ChatDAO;
import com.odfin.persistence.dao.MessageDAO;
import com.odfin.persistence.dao.UserDAO;
import com.odfin.persistence.impl.MySqlMessageDAO;
import com.odfin.persistence.impl.MySqlUserDAO;
import com.odfin.persistence.util.DBHelper;

import java.sql.SQLException;

public class MySqlDAOFactory extends DAOFactory{

    @Override
    public MessageDAO getMessageDAO() {
        return new MySqlMessageDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new MySqlUserDAO();
    }

    @Override
    public ChatDAO getChatDAO() {
        try {
            return new MySqlChatDAO(DBHelper.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException("Error getting Connection", e);
        }
    }
}
