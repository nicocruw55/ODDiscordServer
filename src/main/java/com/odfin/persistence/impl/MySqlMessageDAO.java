package com.odfin.persistence.impl;

import com.odfin.persistence.dao.MessageDAO;
import com.odfin.persistence.domain.Message;
import com.odfin.persistence.util.DBHelper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.odfin.persistence.util.DBHelper.*;

public class MySqlMessageDAO implements MessageDAO {

    public static final String TBL_NAME = "Messages";
    public static final String VIEW_NAME = "MessagesWithUsername";
    public static final String COL_ID = "ID";
    public static final String COL_USER = "user_ID";
    public static final String COL_NAME = "name";
    public static final String COL_CHANNEL = "channel_ID";
    public static final String COL_CONTENT = "content";
    public static final String COL_TIMESTAMP = "timestamp";

    public Message createMessage(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setId(rs.getInt(COL_ID));
        message.setSenderId(rs.getInt(COL_USER));
        message.setName(rs.getString(COL_NAME));
        message.setChannelId(rs.getInt(COL_CHANNEL));
        message.setContent(rs.getString(COL_CONTENT));
        message.setTimestamp(rs.getTimestamp(COL_TIMESTAMP).toLocalDateTime());
        return message;
    }

    @Override
    public Message getMessageById(int messageId) throws SQLException {
        String query = SELECT + "*" + FROM + VIEW_NAME + WHERE + COL_ID + " = ?";

        Connection conn = DBHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, messageId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) return createMessage(rs);

        return null;
    }

    @Override
    public List<Message> getAllMessages() throws SQLException {
        String query = SELECT + "*" + FROM + VIEW_NAME + " ORDER BY " + COL_TIMESTAMP + " ASC";

        List<Message> messages = new ArrayList<>();
        Connection conn = DBHelper.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) messages.add(createMessage(rs));

       // messages.sort(Comparator.comparing(Message::getTimestamp));

        return messages;
    }

    @Override
    public List<Message> getAllMessagesByChannelId(int channelId) throws SQLException {
        List<Message> messages = new ArrayList<>();

        String query = SELECT + "*" + FROM + VIEW_NAME + WHERE + COL_CHANNEL + " = " + channelId + " ORDER BY " + COL_TIMESTAMP + " ASC";

        Connection conn = DBHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) messages.add(createMessage(rs));

       // messages.sort(Comparator.comparing(Message::getTimestamp));

        return messages;
    }

    @Override
    public List<Message> getAllMessagesByChannelIdLazyLoading(int channelId) throws SQLException {
        return null;
    }

    @Override
    public Message updateMessage(Message message) throws SQLException {
        String query = UPDATE + TBL_NAME + SET + COL_USER + " = ?, " + COL_CONTENT + " = ?, " + COL_TIMESTAMP + " = ?, " + COL_CHANNEL + " = ? " + WHERE + COL_ID + " = ?";

        Connection conn = DBHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, message.getSenderId());
        stmt.setString(2, message.getContent());
        stmt.setTimestamp(3, Timestamp.valueOf(message.getTimestamp()));
        stmt.setInt(4, message.getChannelId());
        stmt.setInt(5, message.getId());
        stmt.executeUpdate();

        return getMessageById(message.getId());
    }

    public Message insertMessage(Message message) throws SQLException {
        String query = INSERT_INTO + TBL_NAME + " (" + COL_USER + ", " + COL_CONTENT + ", " + COL_TIMESTAMP + ", " + COL_CHANNEL + ") " + VALUES + "(?, ?, ?, ?)";

        Connection conn = DBHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, message.getSenderId());
        stmt.setString(2, message.getContent());
        stmt.setTimestamp(3, Timestamp.valueOf(message.getTimestamp()));
        stmt.setInt(4, message.getChannelId());
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

    @Override
    public Message sendMessage(String content, int senderId, int channelId) throws SQLException {
        Message message = new Message();
        message.setContent(content);
        message.setChannelId(channelId);
        message.setSenderId(senderId);
        message.setTimestamp(LocalDateTime.now());

        return insertMessage(message);
    }
}
