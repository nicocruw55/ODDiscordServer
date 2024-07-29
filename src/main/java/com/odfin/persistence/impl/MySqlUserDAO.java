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

    @Override
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + COL_NAME + ", " + COL_PASSWORD + ") VALUES (?, ?)";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        }
    }

    @Override
    public User getUserById(Integer userId) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getInt(COL_ID),
                        resultSet.getString(COL_NAME),
                        resultSet.getString(COL_PASSWORD)
                );
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = DBHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt(COL_ID),
                        resultSet.getString(COL_NAME),
                        resultSet.getString(COL_PASSWORD)
                ));
            }
        }
        return users;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET " + COL_NAME + " = ?, " + COL_PASSWORD + " = ? WHERE " + COL_ID + " = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteUser(Integer userId) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }
}
