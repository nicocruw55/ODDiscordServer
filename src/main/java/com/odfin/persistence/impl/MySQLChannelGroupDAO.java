package com.odfin.persistence.impl;

import com.odfin.persistence.dao.ChannelGroupDAO;
import com.odfin.persistence.domain.ChannelGroup;
import com.odfin.persistence.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLChannelGroupDAO implements ChannelGroupDAO {

    private static final String TBL_NAME = "ChannelGroups";
    private static final String COL_CHANNEL_GROUPS_ID = "ChannelGroups.ID";
    private static final String COL_CHANNEL_GROUPS_NAME = "name";

    private static final String JOIN_NAME = "ChannelGroupMembers";
    private static final String COL_JOIN_CHANNEL_GROUP_ID = "ChannelGroupMembers.ChannelGroupID";
    private static final String COL_JOIN_USER_ID = "ChannelGroupMembers.UserID";


    private ChannelGroup createChannelGroup(ResultSet resultSet) throws SQLException {
        ChannelGroup channelGroup = new ChannelGroup();
        channelGroup.setId(resultSet.getInt(COL_CHANNEL_GROUPS_ID));
        channelGroup.setName(resultSet.getString(COL_CHANNEL_GROUPS_NAME));
        return channelGroup;
    }

    @Override
    public ChannelGroup getChannelGroupById(int channelGroupId) throws SQLException {
        String query = "SELECT * FROM " + TBL_NAME + " WHERE " + COL_CHANNEL_GROUPS_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, channelGroupId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new ChannelGroup(resultSet.getString(COL_CHANNEL_GROUPS_ID), resultSet.getString(COL_CHANNEL_GROUPS_NAME));
        }

        return null;
    }

    @Override
    public List<ChannelGroup> getChannelGroupsByUserID(int userID) throws SQLException {

        String query = "SELECT * FROM " + TBL_NAME + " JOIN " + JOIN_NAME +
        " ON " + COL_CHANNEL_GROUPS_ID + " = " + COL_JOIN_CHANNEL_GROUP_ID +  " WHERE " + COL_JOIN_USER_ID +  "= ?";

        List<ChannelGroup> channelGroups = new ArrayList<>();
        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            channelGroups.add(new ChannelGroup(resultSet.getString(COL_CHANNEL_GROUPS_ID), resultSet.getString(COL_CHANNEL_GROUPS_NAME)));
        }

        return channelGroups;
    }

    @Override
    public void updateChannelGroup(ChannelGroup channelGroup) throws SQLException {

        String query = "UPDATE " + TBL_NAME + " SET " + COL_CHANNEL_GROUPS_NAME + " = ? WHERE " + COL_CHANNEL_GROUPS_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, channelGroup.getName());
        statement.setInt(2, channelGroup.getId());
        statement.executeUpdate();

    }

    @Override
    public boolean insertChannelGroup(ChannelGroup channelGroup) throws SQLException {

        String query = "INSERT INTO " + TBL_NAME + " (" + COL_CHANNEL_GROUPS_NAME + ") VALUES (?)";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, channelGroup.getName());
        statement.executeUpdate();

        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            channelGroup.setId(resultSet.getInt(1));
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteChannelGroup(int channelGroupId) throws SQLException {
        String query = "DELETE FROM " + TBL_NAME + " WHERE " + COL_CHANNEL_GROUPS_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, channelGroupId);
        return statement.executeUpdate() > 0;
    }
}
