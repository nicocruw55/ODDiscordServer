package com.odfin.persistence.impl;

import com.odfin.persistence.dao.ServerDAO;
import com.odfin.persistence.domain.Server;
import com.odfin.persistence.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.odfin.persistence.util.DBHelper.*;

public class MySQLServerDAO implements ServerDAO {

    private static final String TBL_NAME = " Server ";
    private static final String COL_SERVER_ID = "Server.ID";
    private static final String COL_SERVER_NAME = "name";

    private static final String JOIN_NAME = " ServerMembers ";
    private static final String COL_JOIN_USER_ID = "ServerMembers.UserID ";


    private Server createServer(ResultSet resultSet) throws SQLException {
        Server server = new Server();
        server.setId(resultSet.getInt(COL_SERVER_ID));
        server.setName(resultSet.getString(COL_SERVER_NAME));
        return server;
    }

    @Override
    public Server getServerById(int serverId) throws SQLException {
        String query = "SELECT * FROM " + TBL_NAME + " WHERE " + COL_SERVER_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, serverId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new Server(resultSet.getString(COL_SERVER_ID), resultSet.getString(COL_SERVER_NAME));
        }

        return null;
    }

    @Override
    public List<Server> getServersByUserID(int userID) throws SQLException {

        String query = "SELECT * FROM " + TBL_NAME + " JOIN " + JOIN_NAME +
        " ON " + COL_SERVER_ID + " = " + COL_JOIN_USER_ID +  " WHERE " + COL_JOIN_USER_ID +  "= ?";

        List<Server> servers = new ArrayList<>();
        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println("Server ID: " + resultSet.getString(COL_SERVER_ID) + " Server Name: " + resultSet.getString(COL_SERVER_NAME));
            servers.add(new Server(resultSet.getString(COL_SERVER_ID), resultSet.getString(COL_SERVER_NAME)));
        }

        return servers;
    }

    @Override
    public Server updateServer(Server server) throws SQLException {
        return null;
    }

    @Override
    public Server insertServer(Server server) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteServer(int serverId) throws SQLException {
        return false;
    }
}
