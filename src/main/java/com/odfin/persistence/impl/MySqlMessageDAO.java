package com.odfin.persistence.impl;

import com.odfin.persistence.dao.MessageDAO;
import com.odfin.persistence.domain.Message;
import com.odfin.persistence.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.odfin.persistence.util.DBHelper.*;

public class MySqlMessageDAO implements MessageDAO {

    public static final String TBL_NAME = "Messages";
    public static final String COL_ID = "ID";
    public static final String COL_USER = "user_ID";
    public static final String COL_CHANNEL = "channel_ID";
    public static final String COL_CONTENT = "Content";
    public static final String COL_TIMESTAMP = "Timestamp";
    // public static final String COL_MESSAGE_TYPE = "MessageType";

    public Message createMessage(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setId(rs.getInt(COL_ID));
        message.setSenderId(rs.getInt(COL_USER));
        message.setContent(rs.getString(COL_CONTENT));
        message.setTimestamp(rs.getTimestamp(COL_TIMESTAMP).toLocalDateTime());
        return message;
    }

    @Override
    public Message getMessageById(int messageId) throws SQLException {
        String query = SELECT + "*" + FROM + TBL_NAME + WHERE + COL_ID + " = ?";

        Connection conn = DBHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, messageId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) return createMessage(rs);

        return null;
    }

    @Override
    public List<Message> getAllMessages() throws SQLException {
        String query = SELECT + "*" + FROM + TBL_NAME;

        List<Message> messages = new ArrayList<>();
        Connection conn = DBHelper.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) messages.add(createMessage(rs));

        return messages;
    }

    @Override
    public List<Message> getAllMessagesByChannelId(int channelId) throws SQLException {
        List<Message> messages = new ArrayList<>();

        String query = SELECT + "*" + FROM + TBL_NAME + WHERE + COL_CHANNEL + " = " + channelId;

        Connection conn = DBHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        //stmt.setInt(1, channelId);
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) messages.add(createMessage(rs));

        return messages;
    }

    @Override
    public List<Message> getAllMessagesByChannelIdLazyLoading(int channelId) throws SQLException {
        return null;
    }

    @Override
    public Message updateMessage(Message message) throws SQLException {
        String query = UPDATE + TBL_NAME + SET + COL_USER + " = ?, " + COL_CONTENT + " = ?, " + COL_TIMESTAMP + " = ?, " + WHERE + COL_ID + " = ?";

        Connection conn = DBHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, message.getSenderId());
        stmt.setString(2, message.getContent());
        stmt.setTimestamp(3, Timestamp.valueOf(message.getTimestamp()));
        //stmt.setString(4, message.getMessageType().name());
        stmt.setInt(4, message.getId());
        stmt.executeUpdate();

        return getMessageById(message.getId());
    }

    public Message insertMessage(Message message) throws SQLException {
        String query = INSERT_INTO + TBL_NAME + " (" + COL_USER + ", " + COL_CONTENT + ", " + COL_TIMESTAMP + ") " + VALUES + "(?, ?, ?, ?)";

        Connection conn = DBHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, message.getSenderId());
        stmt.setString(2, message.getContent());
        stmt.setTimestamp(3, Timestamp.valueOf(message.getTimestamp()));
        //stmt.setString(4, message.getMessageType().name());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            message.setId(rs.getInt(1));
            return getMessageById(message.getId());
        }
        return null;
    }

    @Override
    public boolean deleteMessage(int id) throws SQLException {
        String query = DELETE + FROM + TBL_NAME + WHERE + COL_ID + " = ?";

        Connection conn = DBHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);

        return stmt.execute();
    }
}
