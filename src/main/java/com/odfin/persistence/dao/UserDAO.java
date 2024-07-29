package com.odfin.persistence.dao;

import com.odfin.persistence.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    User getUserById(Integer userId) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(Integer userId) throws SQLException;
}
