package com.odfin.persistence.impl;

import com.odfin.persistence.dao.ChannelDAO;
import com.odfin.persistence.domain.Channel;
import com.odfin.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.odfin.util.DBHelper.*;

public class MySqlChannelDAO implements ChannelDAO {

    public static final String TBL_NAME = "Channels";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "Name";
    public static final String COL_TOPIC = "Topic";

    public Channel createChannel(ResultSet rs) throws SQLException {
        Channel channel = new Channel();
        channel.setId(rs.getInt(COL_ID));
        channel.setName(rs.getString(COL_NAME));
        channel.setTopic(rs.getString(COL_TOPIC));
        return channel;
    }

    @Override
    public Channel getChannelbyId(int id) throws SQLException {
        String query = SELECT + "*" + FROM + TBL_NAME + WHERE + COL_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return createChannel(resultSet);
        }

        return null;
    }

    @Override
    public List<Channel> getAllChannels() throws SQLException {
        String query = SELECT + "*" + FROM + TBL_NAME;

        List<Channel> channels = new ArrayList<>();
        Connection connection = DBHelper.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            channels.add(createChannel(resultSet));
        }

        return channels;
    }

    @Override
    public List<Channel> getAllChannelsByUserId(int userId) throws SQLException {
        String query = "SELECT c.* FROM " + TBL_NAME + " c " + "JOIN ChannelMembers cm ON c." + COL_ID + " = cm.channelId " + "WHERE cm.userId = ?";

        List<Channel> channels = new ArrayList<>();
        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            channels.add(createChannel(resultSet));
        }

        return channels;
    }

    @Override
    public void updateChannel(Channel channel) throws SQLException {
        String query = UPDATE + TBL_NAME + SET + COL_NAME + " = ?, " + COL_TOPIC + " = ? " + WHERE + COL_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, channel.getName());
        statement.setString(2, channel.getTopic());
        statement.setInt(3, channel.getId());
        statement.executeUpdate();
    }

    @Override
    public void insertChannel(Channel channel) throws SQLException {
        String query = INSERT_INTO + TBL_NAME + " (" + COL_NAME + ", " + COL_TOPIC + ") " + VALUES + "(?, ?)";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, channel.getName());
        statement.setString(2, channel.getTopic());
        statement.executeUpdate();

        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            channel.setId(resultSet.getInt(1));
        }
    }

    @Override
    public void deleteChannel(int id) throws SQLException {
        String query = DELETE + FROM + TBL_NAME + WHERE + COL_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
