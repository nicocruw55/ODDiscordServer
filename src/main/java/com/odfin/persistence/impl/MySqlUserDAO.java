package com.odfin.persistence.impl;

import com.odfin.persistence.dao.UserDAO;
import com.odfin.persistence.domain.User;
import com.odfin.persistence.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.odfin.persistence.util.DBHelper.*;

public class MySqlUserDAO implements UserDAO {

    public static final String TBL_NAME = "Users";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "Name";
    public static final String COL_PASSWORD = "Password";

    public User createUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(COL_ID));
        user.setName(rs.getString(COL_NAME));
        user.setPassword(rs.getString(COL_PASSWORD));

        return user;
    }

    @Override
    public User getUserById(Integer userId) throws SQLException {
        String query = SELECT + "*" + FROM + TBL_NAME + WHERE + COL_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) return createUser(resultSet);

        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        String query = SELECT + "*" + FROM + TBL_NAME;

        List<User> users = new ArrayList<>();
        Connection connection = DBHelper.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) users.add(createUser(resultSet));

        return users;
    }

    @Override
    public User updateUser(User user) throws SQLException {
        String query = UPDATE + TBL_NAME + SET + COL_NAME + " = ?, " + COL_PASSWORD + " = ? " + WHERE + COL_ID + " = ?";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getId());
        statement.executeUpdate();

        return getUserById(user.getId());
    }

    @Override
    public boolean deleteUser(Integer id) throws SQLException {
        String query = DELETE + FROM + TBL_NAME + WHERE + COL_ID + " = ?";

        Connection conn = DBHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);

        return stmt.execute();
    }

    public User login(String username, String password) throws SQLException {
        String query = "SELECT * FROM " + TBL_NAME + " WHERE " + COL_NAME + " = ? AND " + COL_PASSWORD + " = ?";

        Connection conn = DBHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return createUser(rs);
        }

        return null;
    }


    public User insertUser(User user) throws SQLException {
        String query = INSERT_INTO + TBL_NAME + " (" + COL_NAME + ", " + COL_PASSWORD + ") " + VALUES + "(?, ?)";

        Connection connection = DBHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());
        statement.executeUpdate();

        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            user.setId(resultSet.getInt(1));
            return getUserById(user.getId());
        }

        return null;
    }
}
