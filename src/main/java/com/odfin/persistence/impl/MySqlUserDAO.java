package com.odfin.persistence.impl;

import com.odfin.persistence.dao.UserDAO;
import com.odfin.persistence.domain.User;
import com.odfin.persistence.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDAO implements UserDAO {

    private static final String TABLE_NAME = "Users";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";
    private static final String COL_PASSWORD = "Password";

    public User createUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(COL_ID));
        user.setName(rs.getString(COL_NAME));
        user.setPassword(rs.getString(COL_PASSWORD));
        return user;
    }

    @Override
    public User getUserById(Integer userId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(TABLE_NAME).append(" WHERE ").append(COL_ID).append(" = ?");

        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createUser(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(TABLE_NAME);

        try (Connection connection = DBHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql.toString())) {

            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
        }
        return users;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(TABLE_NAME).append(" SET ")
                .append(COL_NAME).append(" = ?, ")
                .append(COL_PASSWORD).append(" = ? ")
                .append("WHERE ").append(COL_ID).append(" = ?");

        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteUser(Integer userId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(TABLE_NAME).append(" WHERE ").append(COL_ID).append(" = ?");

        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }
}
