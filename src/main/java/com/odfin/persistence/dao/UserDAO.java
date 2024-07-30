package com.odfin.persistence.dao;

import com.odfin.persistence.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    User getUserById(Integer userId) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    User updateUser(User user) throws SQLException;
    User insertUser(User user) throws SQLException;
    boolean deleteUser(Integer userId) throws SQLException;
    User login(String username, String password) throws SQLException;

}
