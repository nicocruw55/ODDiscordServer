package com.odfin.persistence.impl;

import com.odfin.persistence.dao.ChannelGroupDAO;
import com.odfin.persistence.domain.ChannelGroup;
import com.odfin.persistence.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLChannelGroupDAO implements ChannelGroupDAO {

    private static final String TBL_NAME = " Server ";
    private static final String COL_SERVER_ID = "Server.ID";
    private static final String COL_SERVER_NAME = "name";

    private static final String JOIN_NAME = " ServerMembers ";
    private static final String COL_JOIN_USER_ID = "ServerMembers.UserID ";


    private ChannelGroup createServer(ResultSet resultSet) throws SQLException {
        ChannelGroup channelGroup = new ChannelGroup();
        channelGroup.setId(resultSet.getInt(COL_SERVER_ID));
        channelGroup.setName(resultSet.getString(COL_SERVER_NAME));
        return channelGroup;
    }

    @Override
    public ChannelGroup getServerById(int serverId) throws SQLException {
        String query = "SELECT * FROM " + TBL_NAME + " WHERE " + COL_SERVER_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, serverId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new ChannelGroup(resultSet.getString(COL_SERVER_ID), resultSet.getString(COL_SERVER_NAME));
        }

        return null;
    }

    @Override
    public List<ChannelGroup> getServersByUserID(int userID) throws SQLException {

        String query = "SELECT * FROM " + TBL_NAME + " JOIN " + JOIN_NAME +
        " ON " + COL_SERVER_ID + " = " + COL_JOIN_USER_ID +  " WHERE " + COL_JOIN_USER_ID +  "= ?";

        List<ChannelGroup> channelGroups = new ArrayList<>();
        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println("ChannelGroup ID: " + resultSet.getString(COL_SERVER_ID) + " ChannelGroup Name: " + resultSet.getString(COL_SERVER_NAME));
            channelGroups.add(new ChannelGroup(resultSet.getString(COL_SERVER_ID), resultSet.getString(COL_SERVER_NAME)));
        }

        return channelGroups;
    }

    @Override
    public void updateServer(ChannelGroup channelGroup) throws SQLException {

        String query = "UPDATE " + TBL_NAME + " SET " + COL_SERVER_NAME + " = ? WHERE " + COL_SERVER_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, channelGroup.getName());
        statement.setInt(2, channelGroup.getId());
        statement.executeUpdate();

    }

    @Override
    public void insertServer(ChannelGroup channelGroup) throws SQLException {

        String query = "INSERT INTO " + TBL_NAME + " (" + COL_SERVER_NAME + ") VALUES (?)";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, channelGroup.getName());
        statement.executeUpdate();

        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            channelGroup.setId(resultSet.getInt(1));
        }
    }

    @Override
    public boolean deleteServer(int serverId) throws SQLException {
        String query = "DELETE FROM " + TBL_NAME + " WHERE " + COL_SERVER_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, serverId);
        return statement.executeUpdate() > 0;
    }
}
