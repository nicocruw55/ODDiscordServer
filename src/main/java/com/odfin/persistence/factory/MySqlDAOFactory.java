package com.odfin.persistence.factory;

import com.odfin.persistence.dao.ChannelDAO;
import com.odfin.persistence.dao.MessageDAO;
import com.odfin.persistence.dao.UserDAO;
import com.odfin.persistence.impl.MySqlChannelDAO;
import com.odfin.persistence.impl.MySqlMessageDAO;
import com.odfin.persistence.impl.MySqlUserDAO;

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
    public ChannelDAO getChatDAO() {
        return new MySqlChannelDAO();
    }
}
