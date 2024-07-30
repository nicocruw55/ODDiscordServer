package com.odfin.facade;

import com.odfin.persistence.dao.UserDAO;
import com.odfin.persistence.domain.User;
import com.odfin.persistence.factory.DAOFactory;
import com.odfin.persistence.impl.MySqlUserDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class UserFacadeImpl extends UnicastRemoteObject implements UserFacade {

    private final UserDAO userDAO;

    public UserFacadeImpl() throws RemoteException {
        super(65300);
        this.userDAO = DAOFactory.getDAOFactory(DAOFactory.MY_SQL).getUserDAO();
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

    @Override
    public User login(String username, String password) throws RemoteException {
        try {
            return userDAO.login(username, password);
        } catch (SQLException e) {
            throw new RemoteException("Error logging in", e);
        }
    }

}
