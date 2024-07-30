package com.odfin.persistence.impl;

import com.odfin.persistence.dao.ChatDAO;
import com.odfin.persistence.util.DBHelper;

import java.sql.Connection;

public class MySqlChatDAO {
    public MySqlChatDAO(Connection connection) {
        try {
            DBHelper.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Error getting Connection", e);
        }
    }
}
