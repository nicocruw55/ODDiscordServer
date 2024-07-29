package com.odfin.persistence.impl;



import com.odfin.persistence.dao.MessageDAO;
import com.odfin.persistence.domain.Message;
import com.odfin.persistence.domain.MessageType;
import com.odfin.persistence.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMessageDAO implements MessageDAO {

    public static final String TBL_NAME = "Messages";
    public static final String COL_ID = "ID";
    public static final String COL_SENDER = "SenderID";
    public static final String COL_CHAT = "ChatID";
    public static final String COL_CONTENT = "Content";
    public static final String COL_TIMESTAMP = "Timestamp";
    public static final String COL_MESSAGE_TYPE = "MessageType";

    private Connection getConnection() throws SQLException {
        // Hier sollte die tatsächliche Implementierung zur Verbindung zur Datenbank erfolgen
        return DriverManager.getConnection("jdbc:yourdatabaseurl", "username", "password");
    }

    @Override
    public void createMessage(Message message) throws SQLException {
        String query = "INSERT INTO " + TBL_NAME + " (" + COL_SENDER + ", " + COL_CHAT + ", " + COL_CONTENT + ", " + COL_TIMESTAMP + ", " + COL_MESSAGE_TYPE + ") VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, message.getSender().getId());
            stmt.setLong(2, message.getChatId());
            stmt.setString(3, message.getContent());
            stmt.setTimestamp(4, Timestamp.valueOf(message.getTimestamp()));
            stmt.setString(5, message.getMessageType().name());
            stmt.executeUpdate();
        }
    }

    @Override
    public Message getMessageById(Long messageId) throws SQLException {
        String query = "SELECT * FROM " + TBL_NAME + " WHERE " + COL_ID + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, messageId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Message(
                        rs.getLong(COL_ID),
                        getUserById(rs.getLong(COL_SENDER)),
                        rs.getLong(COL_CHAT),
                        rs.getString(COL_CONTENT),
                        rs.getTimestamp(COL_TIMESTAMP).toLocalDateTime(),
                        MessageType.valueOf(rs.getString(COL_MESSAGE_TYPE))
                );
            }
            return null;
        }
    }

    @Override
    public List<Message> getAllMessages() throws SQLException {
        String query = "SELECT * FROM " + TBL_NAME;
        List<Message> messages = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                messages.add(new Message(
                        rs.getLong(COL_ID),
                        getUserById(rs.getLong(COL_SENDER)),
                        rs.getLong(COL_CHAT),
                        rs.getString(COL_CONTENT),
                        rs.getTimestamp(COL_TIMESTAMP).toLocalDateTime(),
                        MessageType.valueOf(rs.getString(COL_MESSAGE_TYPE))
                ));
            }
        }
        return messages;
    }

    @Override
    public void updateMessage(Message message) throws SQLException {
        String query = "UPDATE " + TBL_NAME + " SET " +
                COL_SENDER + " = ?, " +
                COL_CHAT + " = ?, " +
                COL_CONTENT + " = ?, " +
                COL_TIMESTAMP + " = ?, " +
                COL_MESSAGE_TYPE + " = ? " +
                "WHERE " + COL_ID + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, message.getSender().getId());
            stmt.setLong(2, message.getChatId());
            stmt.setString(3, message.getContent());
            stmt.setTimestamp(4, Timestamp.valueOf(message.getTimestamp()));
            stmt.setString(5, message.getMessageType().name());
            stmt.setLong(6, message.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMessage(Long messageId) throws SQLException {
        String query = "DELETE FROM " + TBL_NAME + " WHERE " + COL_ID + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, messageId);
            stmt.executeUpdate();
        }
    }

    private User getUserById(Long userId) throws SQLException {
        // Hier eine Methode implementieren, die einen User anhand der ID aus der Datenbank abruft
        String query = "SELECT * FROM Users WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getLong("ID"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Email"),
                        rs.getBoolean("OnlineStatus")
                );
            }
            return null;
        }
    }
}
