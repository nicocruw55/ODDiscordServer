package com.odfin.facade;

import com.odfin.persistence.dao.UserDAO;
import com.odfin.persistence.domain.User;
import com.odfin.persistence.impl.MySqlUserDAO;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class UserFacadeImpl implements UserFacade {

    private final UserDAO userDAO;

    // Konstruktor, der die DAO initialisiert
    public UserFacadeImpl() throws RemoteException {
        super();
        this.userDAO = new MySqlUserDAO();
    }

    @Override
    public void createUser(User user) throws RemoteException {
        try {
            userDAO.createUser(user);
        } catch (SQLException e) {
            throw new RemoteException("Error creating user", e);
        }
    }

    @Override
    public User getUserById(Integer userId) throws RemoteException {
        try {
            return userDAO.getUserById(userId);
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving user", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws RemoteException {
        try {
            return userDAO.getAllUsers();
        } catch (SQLException e) {
            throw new RemoteException("Error retrieving all users", e);
        }
    }

    @Override
    public void updateUser(User user) throws RemoteException {
        try {
            userDAO.updateUser(user);
        } catch (SQLException e) {
            throw new RemoteException("Error updating user", e);
        }
    }

    @Override
    public void deleteUser(Integer userId) throws RemoteException {
        try {
            userDAO.deleteUser(userId);
        } catch (SQLException e) {
            throw new RemoteException("Error deleting user", e);
        }
    }
}
