package com.odfin.persistence.impl;

import com.odfin.persistence.dao.MessageDAO;
import com.odfin.persistence.domain.Message;
import com.odfin.persistence.domain.MessageType;
import com.odfin.persistence.domain.User;
import com.odfin.persistence.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMessageDAO implements MessageDAO {

    public static final String TBL_NAME = "Messages";
    public static final String COL_ID = "ID";
    public static final String COL_USER = "UserID";
    public static final String COL_CONTENT = "Content";
    public static final String COL_TIMESTAMP = "Timestamp";
    public static final String COL_MESSAGE_TYPE = "MessageType";

    public Message createMessage(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setId(rs.getInt(COL_ID));
        message.setSenderId(rs.getInt(COL_USER));
        message.setContent(rs.getString(COL_CONTENT));
        message.setTimestamp(rs.getTimestamp(COL_TIMESTAMP).toLocalDateTime());
        message.setMessageType(MessageType.valueOf(rs.getString(COL_MESSAGE_TYPE)));
        return message;
    }

    @Override
    public Message getMessageById(Integer messageId) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(TBL_NAME).append(" WHERE ").append(COL_ID).append(" = ?");

        try (Connection conn = DBHelper.getConnection(); PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            stmt.setInt(1, messageId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return createMessage(rs);
            }
            return null;
        }
    }

    @Override
    public List<Message> getAllMessages() throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(TBL_NAME);

        List<Message> messages = new ArrayList<>();

        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query.toString())) {

            while (rs.next()) {
                messages.add(createMessage(rs));
            }

        } catch (SQLException e) {
            throw new SQLException("Error retrieving all messages", e);
        }

        return messages;
    }

    @Override
    public void updateMessage(Message message) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(TBL_NAME).append(" SET ")
                .append(COL_USER).append(" = ?, ")
                .append(COL_CONTENT).append(" = ?, ")
                .append(COL_TIMESTAMP).append(" = ?, ")
                .append(COL_MESSAGE_TYPE).append(" = ? ")
                .append("WHERE ").append(COL_ID).append(" = ?");

        try (Connection conn = DBHelper.getConnection(); PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            stmt.setInt(1, message.getSenderId());
            stmt.setString(2, message.getContent());
            stmt.setTimestamp(3, Timestamp.valueOf(message.getTimestamp()));
            stmt.setString(4, message.getMessageType().name());
            stmt.setInt(5, message.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMessage(Integer messageId) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(TBL_NAME).append(" WHERE ").append(COL_ID).append(" = ?");

        try (Connection conn = DBHelper.getConnection(); PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            stmt.setInt(1, messageId);
            stmt.executeUpdate();
        }
    }

    private User getUserById(Integer userId) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM Users WHERE ID = ?");

        try (Connection conn = DBHelper.getConnection(); PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("ID"), rs.getString("Name"), rs.getString("Password"));
                }
            }
        }
        return null;
    }
}
